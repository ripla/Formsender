package org.vaadin.risto.formsender;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinServlet;

@WebServlet(urlPatterns = { "/*", "/VAADIN/*" }, initParams = {
        @WebInitParam(name = "ui", value = "org.vaadin.risto.formsender.FormSenderDemoUI"),
        @WebInitParam(name = "widgetset", value = "org.vaadin.risto.formsender.widgetset.FormSenderDemoWidgetset") })
public class FormSenderServlet extends VaadinServlet {

    private static final long serialVersionUID = -4193522812574616328L;

}