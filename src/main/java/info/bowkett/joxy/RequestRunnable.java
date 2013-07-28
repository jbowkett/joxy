package info.bowkett.joxy;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 28, 2013
 * Time: 10:02:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestRunnable implements Runnable {
  private final Socket proxyClientConnection;
  private RequestReader requestReader;
  private RequestParser requestParser;
  private Filter[] filters;

  private static final int BUFFER_SIZE = 32768;

  public RequestRunnable(Socket proxyClientConnection, RequestReader requestReader, RequestParser requestParser, Filter[] filters) {
    this.proxyClientConnection = proxyClientConnection;
    this.requestReader = requestReader;
    this.requestParser = requestParser;
    this.filters = filters;
  }

  public void run() {
    try {
      final String requestStr = new String(requestReader.readRequest(new BufferedInputStream(proxyClientConnection.getInputStream())));

      final Request request = requestParser.parseRequest(requestStr);
      final String destination = request.getDestination();
      if(destination != null){
        final Socket socket = new Socket(destination, 80);
        final InputStream inputStreamFromRemote = makeRemoteRequest(socket, request);
        final byte[] response = requestReader.readRequest(inputStreamFromRemote);

  //      todo: a separate class
        final List<Filter> withFilters = requiredFiltersFor(request);
        final byte[] augmented = augment(request, response, withFilters);

        returnResponse(augmented, proxyClientConnection);
        System.out.println("done.");
      }
    }
    catch (IOException ioe) {
      System.err.println("Cannot service request : " + ioe.getMessage());
      ioe.printStackTrace(System.err);
    }
  }

  private void returnResponse(byte[] augmented, Socket socket) throws IOException {
    final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    final byte[] buffer = new byte[BUFFER_SIZE];
    final ByteArrayInputStream reader = new ByteArrayInputStream(augmented);
    int index = reader.read(buffer, 0, BUFFER_SIZE);
    while (index != -1) {
      out.write(buffer, 0, index);
      index = reader.read(buffer, 0, BUFFER_SIZE);
    }
    out.flush();
  }

  public BufferedInputStream makeRemoteRequest(Socket socket, Request request) throws IOException {
    socket.setSoTimeout(10 * 1000);

    BufferedOutputStream serverOut = new BufferedOutputStream(socket.getOutputStream());

    // send the request out
    serverOut.write(request.text().getBytes(), 0, request.length());
    serverOut.flush();

    return new BufferedInputStream(socket.getInputStream());
  }


  /**
   * Augments the response from request using filters.  If no filters
   * express interest, response is returned unchanged.
   *
   * @param request
   * @param response
   * @param filters
   * @return the response augmented by all filters.
   */
  private byte[] augment(Request request, byte[] response, List<Filter> filters) {
    byte[] currentResponse = response;
    for (Filter filter : filters) {
      currentResponse = filter.augment(request, currentResponse);
    }
    return currentResponse;
  }

  private List<Filter> requiredFiltersFor(Request request) {
    final List<Filter> required = new ArrayList<Filter>();
    for (Filter filter : filters) {
      if (filter.matches(request)) required.add(filter);
    }
    return required;
  }
}
