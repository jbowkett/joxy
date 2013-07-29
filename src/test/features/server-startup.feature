Feature:  Ensure joxy can startup and be used as a proxy

 Scenario: When  started it starts without an error
 Given a joxy server is started
 And a web browser configured to use joxy
 When I navigate to "http://www.google.com"
 Then I will see "joxy" in the html title

Scenario: Augmented browsing
  Given a joxy server is started with a title augmenter
  And a web browser configured to use joxy
  When I navigate to "http://www.google.com"
  Then I will see "joxy" in the html title

Scenario: URL Filtering
  Given a joxy server is started with an adult content URL filter
  And a web browser configured to use joxy
  When I navigate to "http://www.xxx.com"
  Then I will see "Forbidden" in the html title
