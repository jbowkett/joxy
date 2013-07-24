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
  private volatile boolean listen = false;

  public Server(int port, RequestReader requestReader, RequestParser requestParser) {
    this.port = port;
    this.requestReader = requestReader;
    this.requestParser = requestParser;
  }

  @Override
  public void run() {
    listen = true;
    try{
      final ServerSocket serverSocket = new ServerSocket(port);
      System.out.println("Joxy server started on port :["+port+"]...");

      while(listen){
        final Socket incomingConnection = serverSocket.accept();
        final String requestStr = requestReader.readRequest(incomingConnection);
        final Request request = requestParser.parseRequest(requestStr);

      }

      serverSocket.close();
    } catch (IOException e) {
      System.err.println("Connectivity Exception :" + e.getMessage());
      e.printStackTrace();
    }
    System.out.println("Joxy server shutdown.");
  }

  public void shutdown() {
    listen = false;
  }

  public static void main(String [] args){
    final Server server = new Server(4443, new RequestReader(), new RequestParser());
    server.start();
  }


}
