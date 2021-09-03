package org.apache.camel.support;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.Route;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRAsyncProcessorWrapper;
import com.nr.instrumentation.apache.camel.NRProcessorWrapper;

@Weave(type=MatchType.BaseClass)
public abstract class DefaultConsumer {

	public abstract Endpoint getEndpoint();
	
	public abstract Route getRoute();
	
	public synchronized AsyncProcessor getAsyncProcessor() {
		AsyncProcessor asyncProcessor = Weaver.callOriginal();
		if(asyncProcessor != null && !(asyncProcessor instanceof NRAsyncProcessorWrapper)) {
			return new NRAsyncProcessorWrapper(asyncProcessor,getRoute());
		}
		return asyncProcessor;
	}
	
	public Processor getProcessor() {
		Processor processor = Weaver.callOriginal();
		if(!(processor instanceof NRProcessorWrapper)) {
			return new NRProcessorWrapper(processor,getRoute());
		}
		return processor;
	}
}
