package org.apache.camel.support;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.Route;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRAsyncProcessorStart;
import com.nr.instrumentation.apache.camel.NRProcessorStart;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type=MatchType.BaseClass)
public abstract class DefaultConsumer {

	public abstract Endpoint getEndpoint();
	
	public abstract Route getRoute();
	
	public abstract String getRouteId();
	
	public synchronized AsyncProcessor getAsyncProcessor() {
		AsyncProcessor asyncProcessor = Weaver.callOriginal();
		NRAsyncProcessorStart wrapper = Util.getStartingWrapper(asyncProcessor);
		if(wrapper != null) {
			wrapper.setConsumer(getClass().getSimpleName());
			asyncProcessor = wrapper;
		}
		return asyncProcessor;
	}
	
	public Processor getProcessor() {
		Processor processor = Weaver.callOriginal();
		NRProcessorStart wrapper = Util.getStartingWrapper(processor);
		if(wrapper != null) {
			wrapper.setConsumer(getClass().getSimpleName());
			processor = wrapper;
		}
		return processor;
	}
	
	@Weave
	private static class DefaultConsumerCallback {
		
		private final DefaultConsumer consumer = Weaver.callOriginal();
		
		@SuppressWarnings("unused")
		public void done(boolean doneSync) {
			if(consumer != null) {
				String id = consumer.getRouteId();
				if(id != null && !id.isEmpty()) {
					NewRelic.getAgent().getTracedMethod().setMetricName("Custom","DefaultConsumerCallback",id);
				}
			}
			
			Weaver.callOriginal();
		}
		
	}
}
