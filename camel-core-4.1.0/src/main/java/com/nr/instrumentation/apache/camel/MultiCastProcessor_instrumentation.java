package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.processor.MulticastProcessor")
public abstract class MultiCastProcessor_instrumentation {

	@Trace(dispatcher=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));

		return Weaver.callOriginal();
	}

	@Weave(originalName="org.apache.camel.processor.MulticastProcessor$MulticastTask", type = MatchType.BaseClass)
	protected static abstract class MulticastTask  {

		
		@Trace(dispatcher=true)
		protected void doDone(Exchange exchange, boolean forceExhaust) {
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
			Weaver.callOriginal();
		}

	}

}
