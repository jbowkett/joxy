package info.bowkett.joxy;

import java.net.*;
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

  private ExecutorService executor;
  private final Filter[] filters;
  private final RequestReader requestReader;
  private final RequestParser requestParser;

  public RequestServicer(int poolSize, Filter[] filters, RequestReader requestReader, RequestParser requestParser) {
    this.filters = filters;
    this.requestReader = requestReader;
    this.requestParser = requestParser;
    executor = Executors.newFixedThreadPool(poolSize);
  }

  /**
   * Services the given request asynchronously.
   * Hands off to a member of a worker pool for servicing
   *
   * @param proxyClientConnection
   */
  public void service(final Socket proxyClientConnection) {
    executor.execute(new RequestRunnable(proxyClientConnection, requestReader, requestParser, filters));
  }

  public void shutdown() {
    System.out.println("Servicing last requests...");
    executor.shutdown();
    System.out.println("All requests complete.");
  }
}
