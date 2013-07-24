package info.bowkett.joxy;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 24, 2013
 * Time: 9:48:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestParser {
  public Request parseRequest(String request) {
    final StringTokenizer lines = new StringTokenizer(request, "\n");
    final Map<String, String> values = new HashMap<String, String>();
    while(lines.hasMoreElements()){
      final String line = (String) lines.nextElement();
      final int firstSpaceIndex = line.indexOf(' ');
      if(firstSpaceIndex > -1){
        final String key = line.substring(0, firstSpaceIndex);
        final String value = line.substring(firstSpaceIndex +1);
        values.put(clean(key), value);
      }
    }
    return new Request(values.get("HOST"), values.get("PROXY-CONNECTION"),
      values.get("CACHE-CONTROL"), values.get("ACCEPT"), values.get("USER-AGENT"),
      values.get("ACCEPT-ENCODING"), values.get("ACCEPT-LANGUAGE"),
      values.get("COOKIE"));
  }

  private String clean(String key) {
    return key.replaceAll(" |:", "").toUpperCase();
  }
}
