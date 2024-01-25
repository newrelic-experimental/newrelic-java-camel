package org.apache.camel.impl.engine;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRAsyncProcessorWrapper;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class DefaultAsyncProcessorAwaitManager {

	@Trace
	public void process(AsyncProcessor processor, Exchange exchange) {
		String routeId = exchange.getFromRouteId();
		if(routeId == null || routeId.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","DefaultAsyncProcessorAwaitManager","process",processor.getClass().getSimpleName());
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","DefaultAsyncProcessorAwaitManager","process",processor.getClass().getSimpleName(),routeId);
		}
		NRAsyncProcessorWrapper wrapper = Util.getWrapper(processor);
		if(wrapper != null) {
			processor = wrapper;
		}
		Weaver.callOriginal();
	}
}
