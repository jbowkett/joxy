package info.bowkett.joxy;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 24, 2013
 * Time: 9:58:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Request {
  private final String method;
  private final String destination;
  private final String httpVersion;
  private final String host;
  private final String proxyConnection;
  private final String cacheControl;
  private final String accept;
  private final String userAgent;
  private final String acceptEncoding;
  private final String acceptLanguage;
  private final String cookie;
  private final String requestText;

  public Request(String method, String destination, String httpVersion,
                 String host, String proxyConnection, String cacheControl,
                 String accept, String userAgent, String acceptEncoding,
                 String acceptLanguage, String cookie, String requestText) {
    this.method = method;
    this.destination = destination;
    this.httpVersion = httpVersion;
    this.host = host;
    this.proxyConnection = proxyConnection;
    this.cacheControl = cacheControl;
    this.accept = accept;
    this.userAgent = userAgent;
    this.acceptEncoding = acceptEncoding;
    this.acceptLanguage = acceptLanguage;
    this.cookie = cookie;
    this.requestText = requestText;
  }

  public String getHost() {
    return host;
  }

  public String getProxyConnection() {
    return proxyConnection;
  }

  public String getCacheControl() {
    return cacheControl;
  }

  public String getAccept() {
    return accept;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public String getAcceptEncoding() {
    return acceptEncoding;
  }

  public String getAcceptLanguage() {
    return acceptLanguage;
  }

  public String getCookie() {
    return cookie;
  }

  public String text() {
    return requestText;
  }

  public int length() {
    return requestText.length();
  }

  public String getMethod() {
    return method;
  }

  public String getDestination() {
    return destination;
  }

  public String getHttpVersion() {
    return httpVersion;
  }
}
