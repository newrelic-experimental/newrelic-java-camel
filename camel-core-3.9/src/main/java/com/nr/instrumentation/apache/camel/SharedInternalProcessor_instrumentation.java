package com.nr.instrumentation.apache.camel;

import java.util.HashMap;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.impl.engine.SharedCamelInternalProcessor")
public class SharedInternalProcessor_instrumentation {

	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback originalCallback, AsyncProcessor processor, Processor resultProcessor) {
		String routeId = exchange != null ? exchange.getFromRouteId() : null;
		if(routeId != null) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "SharedCamelInternalProcessor", "Camel","SharedCamelInternalProcessor",routeId);
		}
		if (processor != null) {
			HashMap<String, Object> attributes = new HashMap<>();
			Util.recordExchange(attributes, exchange);
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void process(Exchange exchange, AsyncProcessor processor, Processor resultProcessor) {
		String routeId = exchange != null ? exchange.getFromRouteId() : null;
		if(routeId != null) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "SharedCamelInternalProcessor", "Camel","SharedCamelInternalProcessor",routeId);
		}
		if (processor != null) {
			HashMap<String, Object> attributes = new HashMap<>();
			Util.recordExchange(attributes, exchange);
		}
		Weaver.callOriginal();
	}

}
