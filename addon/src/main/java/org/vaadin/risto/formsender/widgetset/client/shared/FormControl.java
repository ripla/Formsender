package org.vaadin.risto.formsender.widgetset.client.shared;

import java.util.Map;

import com.vaadin.shared.communication.ClientRpc;

/**
 * Client-side RPC for sending the form.
 * 
 * @author Risto Yrjänä / Vaadin }>
 * 
 */
public interface FormControl extends ClientRpc {

    void send(Method formMethod, Map<String, String> values, String formTarget,
            String formAction);
}