package info.bowkett.joxy.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import info.bowkett.joxy.Server;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;

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

//    @Before
//    public void setUp() {
//        driver = new ChromeDriver();
//    }


  @Given("^a joxy server is started$")
  public void a_joxy_server_is_started() throws Throwable {
    server = new Server(PROXY_SERVER_PORT);
    server.start();
  }

  @Given("^a web browser configured to use joxy$")
  public void a_web_browser_configured_to_use_joxy() throws Throwable {
    final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setCapability("chrome.switches", Arrays.asList("--proxy-server=http://localhost:" + PROXY_SERVER_PORT));
    driver = new ChromeDriver();
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

  @After
  public void tearDown() {
    driver.close();
  }
}
