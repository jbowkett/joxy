package info.bowkett.joxy;

import java.net.Socket;
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

  public RequestServicer(int poolSize) {
    executor = Executors.newFixedThreadPool(poolSize);
  }

  /**
   * Services the given request asynchronously.
   * Hands off to a member of a worker pool for servicing
   * @param request
   * @param incomingConnection
   */
  public void service(Request request, Socket incomingConnection) {
//    executor.
    
  }

  public void shutdown() {
    System.out.println("Servicing last requests...");
    executor.shutdown();
    System.out.println("All requests complete.");
  }
}
