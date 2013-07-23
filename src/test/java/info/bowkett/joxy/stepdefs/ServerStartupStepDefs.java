package info.bowkett.joxy.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 22, 2013
 * Time: 10:22:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerStartupStepDefs {


  protected WebDriver driver = new ChromeDriver();

//    @Before
//    public void setUp() {
//        driver = new ChromeDriver();
//    }


  @Given("^a joxy server is started$")
  public void a_joxy_server_is_started() throws Throwable {
    // Express the Regexp above with the code you wish you had
//        throw new PendingException();
  }

  @Given("^a web browser configured to use joxy$")
  public void a_web_browser_configured_to_use_joxy() throws Throwable {
    // Express the Regexp above with the code you wish you had
//        throw new PendingException();
  }

  @When("^I navigate to a website$")
  public void I_navigate_to_a_website() throws Throwable {
    // Express the Regexp above with the code you wish you had
    driver.get("http://www.google.co.uk");
  }

  @Then("^I will see that website in the browser$")
  public void I_will_see_that_website_in_the_browser() throws Throwable {
    final String title = driver.getTitle();
    System.out.println("title = " + title);
    // Express the Regexp above with the code you wish you had
//        throw new PendingException();
  }

  @After
  public void tearDown() {
    driver.close();
  }


}
