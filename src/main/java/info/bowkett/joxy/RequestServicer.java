package info.bowkett.joxy;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 25, 2013
 * Time: 5:43:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestServicer {
  private static final int BUFFER_SIZE = 32768;
  private ExecutorService executor;
  private final Filter[] filters;

  public RequestServicer(int poolSize, Filter[] filters) {
    this.filters = filters;
    executor = Executors.newFixedThreadPool(poolSize);
  }

  /**
   * Services the given request asynchronously.
   * Hands off to a member of a worker pool for servicing
   *
   * @param request
   * @param proxyClientConnection
   * @param requestReader
   */
  public void service(final Request request, final Socket proxyClientConnection, final RequestReader requestReader) {
    executor.execute(new Runnable() {
      public void run() {

        try{
          final List<Filter> withFilters = requiredFiltersFor(request);
          System.out.println("opening remote connection...");
          final URLConnection outboundConnection = openRemote("http://google.co.uk");
          System.out.println("connection open.");
          if (outboundConnection.getContentLengthLong() > 0) {
            final byte[] response = requestReader.readRequest(outboundConnection.getInputStream());
            System.out.println("response read.");
            final byte[] augmented = augment(request, response, withFilters);
            returnResponse(augmented, proxyClientConnection);
          }
          System.out.println("done.");
        }
        catch(IOException ioe){
          System.err.println("Cannot service request to ["+request.getHost()+"] "+ioe.getMessage());
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

      /**
       * Augments the response from request using filters.  If no filters
       * express interest, response is returned unchanged.
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


      public URLConnection openRemote(String urlStr) throws IOException {
        final URL url = new URL(urlStr);
        final URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        //avoid doing HTTP Posts for the timebeing
        conn.setDoOutput(false);
        return conn;
      }
    });
  }

  private List<Filter> requiredFiltersFor(Request request) {
    final List<Filter> required = new ArrayList<Filter>();
    for (Filter filter : filters) {
      if (filter.matches(request)) required.add(filter);
    }
    return required;
  }

  public void shutdown() {
    System.out.println("Servicing last requests...");
    executor.shutdown();
    System.out.println("All requests complete.");
  }
}
