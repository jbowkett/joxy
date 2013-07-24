package info.bowkett.joxy;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 23, 2013
 * Time: 8:45:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Server extends Thread {
  private final int port;
  private volatile boolean listen = false;

  public Server(int port) {
    this.port = port;
  }

  @Override
  public void run() {
    listen = true;

    while(listen){

    }
  }

  public void shutdown() {
    listen = false;
  }
}
