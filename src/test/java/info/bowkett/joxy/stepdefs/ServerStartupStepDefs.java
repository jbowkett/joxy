package info.bowkett.joxy.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import info.bowkett.joxy.*;
import org.junit.Assert;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 22, 2013
 * Time: 10:22:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerStartupStepDefs {


  private WebDriver driver;
  private Server server;
  private static final int PROXY_SERVER_PORT = 4443;
  private static ChromeDriverService driverService;

//  todo: sort this into a @Beforeclass method
  static {
    driverService = new ChromeDriverService.Builder()
      .usingDriverExecutable(new File(System.getProperty("webdriver.chrome.driver")))
      .usingAnyFreePort()
      .build();
    try {
      driverService.start();
    }
    catch (IOException e) {
      System.err.println("Cannot start driver: " + e.getMessage());
      e.printStackTrace(System.err);
    }
  }


  @Given("^a joxy server is started$")
  public void a_joxy_server_is_started() throws Throwable {
    server = new Server(PROXY_SERVER_PORT, new RequestServicer(5, new RequestReader(), new RequestParser(), new Augmenter(new Filter[0])));
    server.start();
  }

  @Given("^a web browser configured to use joxy$")
  public void a_web_browser_configured_to_use_joxy() throws Throwable {
    final DesiredCapabilities capabilities = DesiredCapabilities.chrome();

    final Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:" + PROXY_SERVER_PORT);

    capabilities.setCapability("proxy", proxy);

    driver = new RemoteWebDriver(driverService.getUrl(), capabilities);
  }

  @Given("^a joxy server is started with a title augmenter$")
  public void a_joxy_server_is_started_with_a_title_augmenter() throws Throwable {
    server = new Server(PROXY_SERVER_PORT, new RequestServicer(5, new RequestReader(), new RequestParser(), new Augmenter(new Filter[]{
      new Filter(){

        public boolean matches(Request request) {
          return true;
        }

        public byte[] augment(Request request, byte[] response) {
          final String augmented = new String(response);
          return augmented.replaceAll("<title>.*</title>|<TITLE>.*</TITLE>", "<title> joxy </title>").getBytes();
        }
      }
    })));
    server.start();
  }

  @Then("^I will see \"([^\"]*)\" in the html title$")
  public void I_will_see_in_the_html_title(String arg1) throws Throwable {
    final String title = driver.getTitle();
    Assert.assertTrue("Expectation not met : Expected title :[" + title +
      "] to contain :[" + arg1 + "]", title.contains(arg1));
  }

  @Given("^a joxy server is started with an adult content URL filter$")
  public void a_joxy_server_is_started_with_an_adult_content_URL_filter() throws Throwable {
    server = new Server(PROXY_SERVER_PORT, new RequestServicer(5, new RequestReader(), new RequestParser(), new Augmenter(new Filter[]{
      new Filter(){

        public boolean matches(Request request) {
          return true;
        }

        public byte[] augment(Request request, byte[] response) {
          if(!banned(request)) return response;

          return ("HTTP/1.0 405\nContent Type: html\n\n"+
            "<html><head><title>Forbidden by joxy</title></head>" +
            "<body><h1>Forbidden by joxy</h1></body></html>").getBytes();
        }

        private boolean banned(Request request) {
          //ban the whole internet!
          return true;
        }
      }
    })));
    server.start();
  }

  @When("^I navigate to \"([^\"]*)\"$")
  public void I_navigate_to(String url) throws Throwable {
    driver.get(url);
  }

  @After
  public void tearDown() {
    if (driver != null) driver.close();
    if (server != null) server.shutdown();
  }
}
