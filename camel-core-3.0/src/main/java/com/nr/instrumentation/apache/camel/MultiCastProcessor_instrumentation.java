package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.processor.ProcessorExchangePair;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.processor.MulticastProcessor")
public abstract class MultiCastProcessor_instrumentation {

	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}

		return Weaver.callOriginal();
	}

	@Weave(originalName="org.apache.camel.processor.MulticastProcessor$MulticastState")
	protected static class MulticastState {
		
		final Exchange original = Weaver.callOriginal();
		
		MulticastState(Exchange original, Iterable<ProcessorExchangePair> pairs, AsyncCallback callback) {
			
		}
		
		@Trace(async=true)
		public void run() {
			Map<String, Object> attributes = new HashMap<String, Object>();
			Util.recordExchange(attributes, original);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
			Token token = original.getProperty(Util.NRTOKENPROPERTY,Token.class);
			if(token != null) {
				token.link();
			}
			
			Weaver.callOriginal();
		}

	}
}
