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
  private final RequestReader requestReader;
  private final RequestParser requestParser;
  private Augmenter augmenter;

  public RequestServicer(int poolSize, RequestReader requestReader, RequestParser requestParser, Augmenter augmenter) {
    this.augmenter = augmenter;
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
    executor.execute(new RequestRunnable(proxyClientConnection, requestReader, requestParser, augmenter));
  }

  public void shutdown() {
    System.out.println("Servicing last requests...");
    executor.shutdown();
    System.out.println("All requests complete.");
  }
}
