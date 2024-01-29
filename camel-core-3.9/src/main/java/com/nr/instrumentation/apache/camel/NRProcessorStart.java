package com.nr.instrumentation.apache.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class NRProcessorStart implements Processor {
	
	private static boolean isTransformed = false;
	
	private Processor delegate = null;
	private String consumer = null;
	
	public NRProcessorStart(Processor processor) {
		delegate = processor;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = false;
		}
	}

	@Override
	@Trace(dispatcher = true)
	public void process(Exchange exchange) throws Exception {
		if(consumer != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","SyncConsumerStart",consumer,"process-sync");
		}
		delegate.process(exchange);
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
}
