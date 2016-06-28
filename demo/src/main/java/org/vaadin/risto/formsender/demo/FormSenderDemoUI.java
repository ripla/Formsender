package org.vaadin.risto.formsender.demo;

import com.google.common.html.HtmlEscapers;
import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.SynchronizedRequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.risto.formsender.FormSenderBuilder;
import org.vaadin.risto.formsender.client.shared.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.logging.Logger;

@Theme(ValoTheme.THEME_NAME)
public class FormSenderDemoUI extends UI {

    private static final long serialVersionUID = 8564331787044638071L;
    public static final String PASSWORD_PARAM = "password";
    public static final String NAME_PARAM = "name";

    @SuppressWarnings("unchecked")
    @Override
    public void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Formsender Demo");

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        setContent(mainLayout);

        Panel panel = new Panel("Login");
        panel.setWidth("400px");
        VerticalLayout panelContent = new VerticalLayout();
        panel.setContent(panelContent);

        FormLayout loginLayout = new FormLayout();
        loginLayout.setMargin(true);
        final TextField usernameField = new TextField("Username");
        final PasswordField passwordField = new PasswordField("Password");
        Button submit = new Button("Submit");

        final NativeSelect select = new NativeSelect("Choose submit action");
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("caption", String.class, "");

        select.addItem("app").getItemProperty("caption")
                .setValue("This application");
        select.addItem("printparameters.jsp").getItemProperty("caption")
                .setValue("JSP page");
        select.setItemCaptionPropertyId("caption");
        select.select("printparameters.jsp");

        loginLayout.addComponent(usernameField);
        loginLayout.addComponent(passwordField);
        loginLayout.addComponent(select);
        loginLayout.addComponent(submit);

        panel.setContent(loginLayout);
        mainLayout.addComponent(panel);
        mainLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        String name = (String) VaadinSession.getCurrent()
                .getAttribute(NAME_PARAM);
        String password = (String) VaadinSession.getCurrent()
                .getAttribute(PASSWORD_PARAM);
        Logger.getLogger(this.getClass().getSimpleName())
                .info(request.getParameterMap().toString());
        if (!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(password)) {
            Notification.show("Name: " + name + " Password: " + password,
                    Notification.Type.TRAY_NOTIFICATION);
            VaadinSession.getCurrent().setAttribute(NAME_PARAM, null);
            VaadinSession.getCurrent().setAttribute(PASSWORD_PARAM, null);
        }

        submit.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                FormSenderBuilder.create().withUI(getUI())
                        .withAction((String) select.getValue())
                        .withTarget("_self").withMethod(Method.POST)
                        .withValue(NAME_PARAM, usernameField.getValue())
                        .withValue(PASSWORD_PARAM, passwordField.getValue())
                        .submit();
            }
        });
    }

    @WebServlet(urlPatterns = { "/app/*", "/VAADIN/*" })
    @VaadinServletConfiguration(ui = FormSenderDemoUI.class, productionMode = false, widgetset = "org.vaadin.risto.formsender.demo.Widgetset")
    public static class DemoServlet extends VaadinServlet
            implements SessionInitListener {

        @Override
        public void sessionInit(SessionInitEvent event)
                throws ServiceException {

            VaadinSession.getCurrent()
                    .addRequestHandler(new SynchronizedRequestHandler() {

                        @Override
                        public boolean synchronizedHandleRequest(
                                VaadinSession session, VaadinRequest request,
                                VaadinResponse response) throws IOException {
                            if ("POST".equals(request.getMethod())) {
                                session.setAttribute(NAME_PARAM, null);
                                session.setAttribute(PASSWORD_PARAM, null);
                                final String name = request
                                        .getParameter(NAME_PARAM);
                                final String password = request
                                        .getParameter(PASSWORD_PARAM);
                                if (!Strings.isNullOrEmpty(name)
                                        && !Strings.isNullOrEmpty(password)) {
                                    session.setAttribute(NAME_PARAM,
                                            HtmlEscapers.htmlEscaper()
                                                    .escape(name));
                                    session.setAttribute(PASSWORD_PARAM,
                                            HtmlEscapers.htmlEscaper()
                                                    .escape(password));
                                }
                            }
                            return false;
                        }
                    });
        }

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(this);

        }
    }
}
