# Joxy

Joxy is a Java proxy server allowing for augmented browsing and URL/content
filtering plugins.  It is implemented in Java, using basic Java sockets and
threads.

## Cucumber notes

Joxy uses [Cucumber-JVM](https://github.com/cucumber/cucumber-jvm) (see also [here](http://cukes.info/install-cucumber-jvm.html)), and the Chrome Driver for Selenium.  Selenium
requires a bridge to allow it to access the Chromium automation toolkit.
This can be downloaded [here](https://code.google.com/p/selenium/wiki/ChromeDriver)

The path to the bridge executable can then be set in the system property
`webdriver.chrome.driver` when running the scenarios.  This is currently done
in Joxy's `pom.xml` in the line :

`-Dwebdriver.chrome.driver=/Users/jbowkett/other/chromedriver/chromedriver`.

 
