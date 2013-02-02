package org.vaadin.risto.formsender;

import java.util.Map;

import com.apple.eawt.Application;
import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class FormsenderApplication extends Application implements
        ParameterHandler {

    private static final long serialVersionUID = 8564331787044638071L;
    private Window mainWindow;

    @Override
    public void init() {
        mainWindow = new Window("Formsender Application");

        mainWindow.addParameterHandler(this);

        Panel panel = new Panel();
        panel.setWidth("400px");

        final FormSender formSender = new FormSender();

        formSender.setFormMethod(FormSender.Method.POST);

        LoginForm loginForm = new LoginForm();
        loginForm.addListener(new LoginListener() {

            private static final long serialVersionUID = -7672714513229340441L;

            public void onLogin(LoginEvent event) {
                formSender.addValue("name",
                        (String) event.getLoginParameter("username"));
                formSender.addValue("password",
                        event.getLoginParameter("password"));

                formSender.submit();
            }
        });

        NativeSelect select = new NativeSelect("Choose submit target");
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("caption", String.class, "");
        select.addItem("").getItemProperty("caption")
                .setValue("This application");
        select.addItem("printparameters.jsp").getItemProperty("caption")
                .setValue("JSP page");
        select.setItemCaptionPropertyId("caption");
        select.addListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = -488987561395827034L;

            public void valueChange(ValueChangeEvent event) {
                formSender.setFormAction((String) event.getProperty()
                        .getValue());
            }
        });
        select.select("printparameters.jsp");

        panel.addComponent(loginForm);
        panel.addComponent(select);
        mainWindow.addComponent(panel);
        mainWindow.addComponent(formSender);

        setMainWindow(mainWindow);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.ParameterHandler#handleParameters(java.util.Map)
     */
    public void handleParameters(Map<String, String[]> parameters) {
        if (parameters.containsKey("name")
                && parameters.containsKey("password")) {
            String name = parameters.get("name")[0];
            String password = parameters.get("password")[0];

            Label label = new Label("Name: " + name + " Password: " + password);

            mainWindow.addComponent(label);
        }
    }

}
