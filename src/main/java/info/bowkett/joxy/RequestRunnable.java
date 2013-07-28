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
  private final Augmenter augmenter;

  private static final int BUFFER_SIZE = 32768;

  public RequestRunnable(Socket proxyClientConnection, RequestReader requestReader,
                         RequestParser requestParser, Augmenter augmenter) {
    this.proxyClientConnection = proxyClientConnection;
    this.requestReader = requestReader;
    this.requestParser = requestParser;
    this.augmenter = augmenter;
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

        final byte[] augmented = augmenter.augment(request, response);

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
}
