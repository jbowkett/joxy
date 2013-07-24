package info.bowkett.joxy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 24, 2013
 * Time: 9:50:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestParserTest {

  private static final String EXAMPLE_REQUEST = "GET http://bbc.co.uk/ HTTP/1.1\n" +
      "Host: bbc.co.uk\n" +
      "Proxy-Connection: keep-alive\n" +
      "Cache-Control: max-age=0\n" +
      "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
      "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36\n" +
      "Accept-Encoding: gzip,deflate,sdch\n" +
      "Accept-Language: en-US,en;q=0.8\n" +
      "Cookie: BBC-UID=85e1435a65301d5e45ba067dc1c81a4816f69c1f94c4d16e4a71047eb2b86e280Mozilla/5.0%20(Macintosh%3b%20Intel%20Mac%20OS%20X%2010_8_2)%20AppleWebKit/537.22%20(KHTML%2c%20like%20Gecko)%20Chrome/25.0.1364; ckns_policy=111; BGUID=45a1c35a55302e128ad97c3ec1293be1551e78743e7873c94375ba2da78ee968; s1=513A50E22A2B02D0; ckpf_ww_mobile_js=on; s_cc=true; c=undefinedDirect%20LoadDirect%20Load; s_ev49=%5B%5B'Direct%2520Load'%2C'1373466309553'%5D%5D; s_sq=%5B%5BB%5D%5D; s_sv_sid=1077057091917; rsi_segs=J08781_10057|J08781_10189|J08781_10639|J08781_0; _em_vt=0419aa66bb422ec9acdf2fbd16cd51dd6ec6916284-0340147151dd6ec6";

  RequestParser parser;
  @Before
  public void setUp(){
    parser = new RequestParser();
  }


  @Test
  public void testParseRequestHost() throws Exception{
    final String actualHost = parser.parseRequest(EXAMPLE_REQUEST).getHost();
    assertEquals("bbc.co.uk", actualHost);
  }

  @Test
  public void testParseRequestProxyConnection() throws Exception{
    final String proxyConnection = parser.parseRequest(EXAMPLE_REQUEST).getProxyConnection();
    assertEquals("keep-alive", proxyConnection);
  }

  @Test
  public void testParseRequestCacheControl() throws Exception{
    final String cacheControl = parser.parseRequest(EXAMPLE_REQUEST).getCacheControl();
    assertEquals("max-age=0", cacheControl);
  }

  @Test
  public void testParseRequestAccept() throws Exception{
    final String accept = parser.parseRequest(EXAMPLE_REQUEST).getAccept();
    assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", accept);
  }

  @Test
  public void testParseRequestUserAgent() throws Exception{
    final String userAgent = parser.parseRequest(EXAMPLE_REQUEST).getUserAgent();
    assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36", userAgent);
  }

  @Test
  public void testParseRequestAcceptEncoding() throws Exception{
    final String acceptEncoding = parser.parseRequest(EXAMPLE_REQUEST).getAcceptEncoding();
    assertEquals("gzip,deflate,sdch", acceptEncoding);
  }

  @Test
  public void testParseRequestAcceptLanguage() throws Exception{
    final String acceptEncoding = parser.parseRequest(EXAMPLE_REQUEST).getAcceptLanguage();
    assertEquals("en-US,en;q=0.8", acceptEncoding);
  }

  @Test
  public void testParseRequestCookie() throws Exception{
    final String cookie = parser.parseRequest(EXAMPLE_REQUEST).getCookie();
    assertEquals("BBC-UID=85e1435a65301d5e45ba067dc1c81a4816f69c1f94c4d16e4a71047eb2b86e280Mozilla/5.0%20(Macintosh%3b%20Intel%20Mac%20OS%20X%2010_8_2)%20AppleWebKit/537.22%20(KHTML%2c%20like%20Gecko)%20Chrome/25.0.1364; ckns_policy=111; BGUID=45a1c35a55302e128ad97c3ec1293be1551e78743e7873c94375ba2da78ee968; s1=513A50E22A2B02D0; ckpf_ww_mobile_js=on; s_cc=true; c=undefinedDirect%20LoadDirect%20Load; s_ev49=%5B%5B'Direct%2520Load'%2C'1373466309553'%5D%5D; s_sq=%5B%5BB%5D%5D; s_sv_sid=1077057091917; rsi_segs=J08781_10057|J08781_10189|J08781_10639|J08781_0; _em_vt=0419aa66bb422ec9acdf2fbd16cd51dd6ec6916284-0340147151dd6ec6", cookie);
  }
}
