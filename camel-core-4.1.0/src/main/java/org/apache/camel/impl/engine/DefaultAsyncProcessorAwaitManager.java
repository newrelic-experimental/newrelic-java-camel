package org.apache.camel.impl.engine;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;

@Weave
public abstract class DefaultAsyncProcessorAwaitManager {

	@Trace(dispatcher = true)
	public void process(AsyncProcessor processor, Exchange exchange) {
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
		Weaver.callOriginal();
	}
}
