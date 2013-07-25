package info.bowkett.joxy.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import info.bowkett.joxy.RequestParser;
import info.bowkett.joxy.RequestReader;
import info.bowkett.joxy.RequestServicer;
import info.bowkett.joxy.Server;
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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Given("^a joxy server is started$")
  public void a_joxy_server_is_started() throws Throwable {
    server = new Server(PROXY_SERVER_PORT, new RequestReader(), new RequestParser(), new RequestServicer(5));
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

  @When("^I navigate to a website$")
  public void I_navigate_to_a_website() throws Throwable {
    driver.get("http://www.google.co.uk");
  }

  @Then("^I will see that website in the browser$")
  public void I_will_see_that_website_in_the_browser() throws Throwable {
    final String title = driver.getTitle();
    //todo: need a much better assertion here - document not null, or check the specific title
    Assert.assertNotNull(title);
  }

  @Given("^a joxy server is started with a title augmenter$")
  public void a_joxy_server_is_started_with_a_title_augmenter() throws Throwable {
    // Express the Regexp above with the code you wish you had
    throw new PendingException();
  }

  @Then("^I will see a message in the html title$")
  public void I_will_see_a_message_in_the_html_title() throws Throwable {
    // Express the Regexp above with the code you wish you had
    throw new PendingException();
  }

  @Given("^a joxy server is started with an adult content URL filter$")
  public void a_joxy_server_is_started_with_an_adult_content_URL_filter() throws Throwable {
    // Express the Regexp above with the code you wish you had
    throw new PendingException();
  }

  @When("^I navigate to \"([^\"]*)\"$")
  public void I_navigate_to(String arg1) throws Throwable {
    // Express the Regexp above with the code you wish you had
    throw new PendingException();
  }

  @Then("^I will see a permission denied message$")
  public void I_will_see_a_permission_denied_message() throws Throwable {
    // Express the Regexp above with the code you wish you had
    throw new PendingException();
  }

  @After
  public void tearDown() {
    if (driver != null) driver.close();
    if (server != null) server.shutdown();
  }
}
