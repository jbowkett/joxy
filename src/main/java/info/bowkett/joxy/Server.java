package info.bowkett.joxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 23, 2013
 * Time: 8:45:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Server extends Thread {
  private final int port;
  private final RequestReader requestReader;
  private RequestParser requestParser;
  private final RequestServicer requestServicer;
  private volatile boolean listen = false;

  public Server(int port, RequestReader requestReader, RequestParser requestParser, RequestServicer requestServicer) {
    this.port = port;
    this.requestReader = requestReader;
    this.requestParser = requestParser;
    this.requestServicer = requestServicer;
  }

  @Override
  public void run() {
    listen = true;
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
      System.out.println("Joxy server started on port :[" + port + "]...");

      while (listen) {
        final Socket incomingConnection = serverSocket.accept();
        final String requestStr = new String(requestReader.readRequest(incomingConnection.getInputStream()));
        final Request request = requestParser.parseRequest(requestStr);
        requestServicer.service(request, incomingConnection, requestReader);
      }
    }
    catch (IOException e) {
      System.err.println("Connectivity Exception :" + e.getMessage());
      e.printStackTrace(System.err);
    }
    finally {
      requestServicer.shutdown();
      try {
        if (serverSocket != null) serverSocket.close();
      }
      catch (Exception e) {}//nothing as closing down anyway
    }
    System.out.println("Joxy server shutdown.");
  }

  public void shutdown() {
    requestServicer.shutdown();
    listen = false;
    System.out.println("Server shutdown complete.");
  }

  public static void main(String[] args) {
    final Server server = new Server(4443, new RequestReader(), new RequestParser(), new RequestServicer(1, new Filter[0]));
    Runtime.getRuntime().addShutdownHook(
      new Thread(new Runnable() {
        public void run() {
          server.shutdown();
        }
      }, "shutdown hook"));

    server.start();
  }
}
