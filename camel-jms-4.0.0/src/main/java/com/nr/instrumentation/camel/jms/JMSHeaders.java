package com.nr.instrumentation.camel.jms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;
import com.newrelic.api.agent.NewRelic;
import jakarta.jms.JMSException;

public class JMSHeaders implements Headers {

	private jakarta.jms.Message message = null;

	public JMSHeaders(jakarta.jms.Message msg) {
		message = msg;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		try {
			return message.getStringProperty(name);
		} catch (JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error getting header: {0}",name);
		}
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> list = new ArrayList<String>();
		try {
			String value = message.getStringProperty(name);
			if(value != null && !value.isEmpty()) {
				list.add(value);
			}
		} catch(JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error getting header: {0}",name);
		}
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		try {
			message.setStringProperty(name, value);
		} catch(JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error setting header: {0}",name);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		try {
			message.setStringProperty(name, value);
		} catch(JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error adding header: {0}",name);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getHeaderNames() {
		try {
			return Collections.list(message.getPropertyNames());
		} catch (JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error finding header names");
		}
		return Collections.emptyList();
	}

	@Override
	public boolean containsHeader(String name) {
		try {
			return message.propertyExists(name);
		} catch (JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error finding header: {0}",name);
		}
		return false;
	}

}
