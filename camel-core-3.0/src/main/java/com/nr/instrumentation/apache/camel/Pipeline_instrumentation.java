package com.nr.instrumentation.apache.camel;

import java.util.Iterator;

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
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Pipeline","process");
		}
		return Weaver.callOriginal();
	}
	
	@Trace(async=true)
	protected void doProcess(Exchange exchange, AsyncCallback callback, Iterator<AsyncProcessor> processors, boolean first) {
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		}
		
		Weaver.callOriginal();
		
	}
}
