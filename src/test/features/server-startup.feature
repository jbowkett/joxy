Feature:  Ensure joxy can startup and be used as a proxy¶
 Scenario: When  started it starts without an error
 Given a joxy server is started
 And a web browser configured to use joxy
 When I navigate to a website
 Then I will see that website in the browser
