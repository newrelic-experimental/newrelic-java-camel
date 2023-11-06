package org.apache.camel.component.seda;

import org.apache.camel.Exchange;
import org.apache.camel.ExtendedExchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class SedaProducer {

	protected void addToQueue(Exchange exchange, boolean copy) {
		CamelHeaders headers = new CamelHeaders(exchange);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		if(exchange instanceof ExtendedExchange) {
			Util.addCompletionIfNeeded((ExtendedExchange)exchange);
		}
		Weaver.callOriginal();
	}
}
