package info.bowkett.joxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 24, 2013
 * Time: 9:29:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestReader {

  public String readRequest(Socket incomingConnection) throws IOException {
    InputStream incomingInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader in = null;
    try {
      incomingInputStream = incomingConnection.getInputStream();
      inputStreamReader = new InputStreamReader(incomingInputStream);
      in = new BufferedReader(inputStreamReader);

      final StringBuilder buffer = new StringBuilder();
      String inputLine;

      while ((inputLine = in.readLine()) != null) {
        System.out.println(inputLine);
        buffer.append(inputLine);
      }
      return buffer.toString();
    }
    catch (IOException e) {
      System.out.println("Cannot read from socket:" + e.getMessage());
      throw e;
    }
    finally {
      try {
        if (in != null) in.close();
      }
      catch (Exception e) {
      }//do nothing as closing
    }
  }

}
