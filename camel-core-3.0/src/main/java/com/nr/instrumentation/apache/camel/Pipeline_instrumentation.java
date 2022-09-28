package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.Pipeline")
public abstract class Pipeline_instrumentation {
	
	private String id = Weaver.callOriginal();
	
	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);

		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		} else {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				exchange.setProperty(Util.NRTOKENPROPERTY, t);
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		if(id != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Pipeline","process",id);
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("PipelineID", id);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Pipeline","process");
		}
		return Weaver.callOriginal();
	}
	
	@Trace(async=true)
	protected void doProcess(Exchange exchange, AsyncCallback callback, Iterator<AsyncProcessor> processors, boolean first) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		}
		
		Weaver.callOriginal();
		
	}
}
