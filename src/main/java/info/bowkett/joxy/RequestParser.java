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
    final Map<String, String> values = new HashMap<String, String>();

    final String lines [] = request.split("\n");
    for (String line : lines) {
      parseLine(values, line);
    }
    parseFirstLine(values, lines[0], request);

    return new Request(values.get("METHOD"), values.get("DESTINATION"), 
      values.get("HTTP-VERSION"), values.get("HOST"), values.get("PROXY-CONNECTION"),
      values.get("CACHE-CONTROL"), values.get("ACCEPT"), values.get("USER-AGENT"),
      values.get("ACCEPT-ENCODING"), values.get("ACCEPT-LANGUAGE"),
      values.get("COOKIE"), request);
  }

  private void parseFirstLine(Map<String, String> values, String line, String request) {
    final String [] components = line.split(" ");
    if(components.length >= 1) values.put("METHOD", components[0]);
    if(components.length >= 2) values.put("DESTINATION", toDestination(components[1]));
    if(components.length >= 3) values.put("HTTP-VERSION", components[2]);

    if(components.length < 3){
      System.out.println("request = " + request);
    }
  }

  private String toDestination(String raw) {
    final String noHttp = raw.replaceAll("http://", "");
    final int index = noHttp.indexOf('/');
    return index < 0 ? noHttp : noHttp.substring(0, index);
  }

  private void parseLine(Map<String, String> values, String line) {
    final int firstSpaceIndex = line.indexOf(' ');
    if(firstSpaceIndex > -1){
      final String key = line.substring(0, firstSpaceIndex);
      final String value = line.substring(firstSpaceIndex +1);
      values.put(clean(key), value);
    }
  }

  /**
   * removes extraneous spaces or colons in the key
   * @param key
   * @return
   */
  private String clean(String key) {
    return key.replaceAll(" |:", "").toUpperCase();
  }
}
