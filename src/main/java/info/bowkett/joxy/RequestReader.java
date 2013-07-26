package info.bowkett.joxy;

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
    byte [] request = new byte[0];
    try {
      final byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead;

      do{
        bytesRead = incomingInputStream.read(buffer, 0, BUFFER_SIZE);
        request = appendBuffer(request, buffer, bytesRead);
      }
      while (bytesRead != -1 && bytesRead == BUFFER_SIZE);

      return request;
    }
    catch (IOException e) {
      System.out.println("Cannot read from socket:" + e.getMessage());
      throw e;
    }
    finally {
      try {
        if (incomingInputStream != null) incomingInputStream.close();
      }
      catch (Exception e) {
      }//do nothing as closing
    }
  }

  private byte[] appendBuffer(byte[] previouslyBuffered, byte[] bufferToAppend, int bytesRead) {
    byte[] expandedBuffer = previouslyBuffered;
    if(bytesRead > -1) {
      expandedBuffer = new byte[previouslyBuffered.length + bytesRead];
      System.arraycopy(bufferToAppend, 0, expandedBuffer, previouslyBuffered.length, bytesRead);
    }
    return expandedBuffer;
  }
}
