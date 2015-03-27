# Formsender add-on for Vaadin

Simple add-on to allow doing HTTP POST calls from the client-side. See [the Directory](http://vaadin.com/addon/formsender) for more info.

To test:

1. Clone the repo.
2. Run *mvn install* to build the project.
3. Run *mvn -pl demo jetty:run-war* to start the demo application
4. Browse to *http://localhost:8080/formsender/app*. From there you can do a HTTP POST to either the app itself or a separate JSP page.