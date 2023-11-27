package org.apache.camel.spi;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.NRAsyncProcessorWrapper;

@Weave(type = MatchType.Interface)
public abstract class AsyncProcessorAwaitManager {

	@Trace(dispatcher = true)
	public void process(AsyncProcessor processor, Exchange exchange) {
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncProcessorAwaitManager",getClass().getSimpleName(),"process");
		NRAsyncProcessorWrapper wrapper = new NRAsyncProcessorWrapper(processor, null);
		processor = wrapper;
		Weaver.callOriginal();
	}
}
