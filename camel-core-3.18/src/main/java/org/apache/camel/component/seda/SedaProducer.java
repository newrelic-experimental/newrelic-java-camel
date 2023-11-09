package org.apache.camel.component.seda;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;

@Weave
public abstract class SedaProducer {

	protected void addToQueue(Exchange exchange, boolean copy) {
		CamelHeaders headers = new CamelHeaders(exchange);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		Weaver.callOriginal();
	}
}
