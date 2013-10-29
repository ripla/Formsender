package org.vaadin.risto.formsender;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.vaadin.ui.UI;

public class FormSenderBuilder {

    public static FormSenderBuilder create() {
        return new FormSenderBuilder();
    }

    protected FormSenderBuilder() {
        // use FormSenderBuilder.create()
    }

    private Method method;
    private UI ui;
    private final Map<String, String> values = new HashMap<String, String>();
    private String action;
    private String target;

    public FormSenderBuilder withMethod(Method method) {
        this.method = method;
        return this;
    }

    public FormSenderBuilder withAction(String action) {
        this.action = action;
        return this;
    }

    public FormSenderBuilder withTarget(String target) {
        this.target = target;
        return this;
    }

    public FormSenderBuilder withUI(UI ui) {
        this.ui = ui;
        return this;
    }

    public FormSenderBuilder withValue(String name, String value) {
        values.put(name, value);
        return this;
    }

    public FormSenderBuilder withValues(Map<String, String> values) {
        this.values.putAll(values);
        return this;
    }

    public void submit() {
        FormSender fs = new FormSender(method);
        fs.setValues(values);
        fs.setFormAction(action);
        fs.setFormTarget(target);

        fs.extend(ui);
        fs.submit();
    }
}