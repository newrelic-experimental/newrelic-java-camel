package com.nr.instrumentation.apache.camel;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.ChoiceProcessor")
public abstract class ChoiceProcessor_instrumentation {

	public abstract String getId();
	
	public boolean process(Exchange exchange, AsyncCallback callback) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("ChoiceProcessor-ID", getId());
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "ChoiceProcessor", new String[] {"ChoiceProcessor",routeID});
		}
		return Weaver.callOriginal();
	}
	
}
