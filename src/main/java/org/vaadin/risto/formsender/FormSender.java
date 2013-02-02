package org.vaadin.risto.formsender;

import java.util.HashMap;

/**
 * Component that allows submitting post or get requests from a vaadin
 * application. Submitting a form with this component will redirect the user
 * away from the current application.
 * 
 * @author Risto Yrjänä / IT Mill Ltd.
 * 
 */
@com.vaadin.ui.ClientWidget(org.vaadin.risto.formsender.widgetset.client.ui.VFormSender.class)
public class FormSender extends AbstractComponent {

    private static final long serialVersionUID = -2072702453956623077L;

    /**
     * Method to use when submitting the form.
     * 
     * @author Risto Yrjänä / IT Mill Ltd.
     * 
     */
    public enum Method {
        POST, GET
    };

    private Method formMethod;

    private HashMap<String, String> values;

    private boolean submitForm;

    private String formTarget;

    private String formAction;

    public FormSender() {
        this(Method.POST);
    }

    public FormSender(Method formMethod) {
        this.formMethod = formMethod;
        values = new HashMap<String, String>();
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        if (submitForm) {
            target.addAttribute("submit", true);
            target.addAttribute("target", formTarget.toString());
            target.addAttribute("method", formMethod.toString());
            target.addAttribute("action", getFormAction().toString());
            target.startTag("values");
            for (String name : values.keySet()) {
                target.addAttribute(name, values.get(name));
            }
            target.endTag("values");

            submitForm = false;
        }
    }

    /**
     * Submit the form with the current values, target and method.
     */
    public void submit() {
        submitForm = true;
        requestRepaint();
    }

    /**
     * Set the method to be used when submitting the form.
     * 
     * @param formMethod
     */
    public void setFormMethod(Method formMethod) {
        this.formMethod = formMethod;
    }

    public Method getFormMethod() {
        return formMethod;
    }

    /**
     * Set the name / value pairs to use when submitting the form.
     * 
     * @param values
     *            the values to set
     */
    public void setValues(HashMap<String, String> values) {
        this.values = values;
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    /**
     * Add a name / value pair.
     * 
     * @param name
     * @param value
     */
    public void addValue(String name, String value) {
        values.put(name, value);
    }

    /**
     * Set the submit target. This can be a full valid URL or a part of it
     * relative to the current application.
     * 
     * @param formTarget
     */
    public void setFormTarget(String formTarget) {
        this.formTarget = formTarget;
    }

    public String getFormTarget() {
        return formTarget;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
}
