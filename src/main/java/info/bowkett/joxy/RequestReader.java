package info.bowkett.joxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 24, 2013
 * Time: 9:29:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestReader {
  private static final int BUFFER_SIZE = 32768;

  public byte [] readRequest(InputStream incomingInputStream) throws IOException {
    try {
      final byte[] readBuffer = new byte[BUFFER_SIZE];

      final ByteArrayOutputStream bufferToReturn = new ByteArrayOutputStream();

      int bytesRead;

      while((bytesRead = incomingInputStream.read(readBuffer)) >= 0 ){
        bufferToReturn.write(readBuffer, 0, bytesRead);
      }
      bufferToReturn.flush();
      return bufferToReturn.toByteArray();
    }
    catch (IOException e) {
      System.out.println("Cannot read from socket:" + e.getMessage());
      throw e;
    }
    finally {
      try {
//        if (incomingInputStream != null) incomingInputStream.close();
      }
      catch (Exception e) {
      }//do nothing as closing
    }
  }
}
