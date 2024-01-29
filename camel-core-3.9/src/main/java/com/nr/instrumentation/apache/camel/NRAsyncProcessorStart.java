package com.nr.instrumentation.apache.camel;

import java.util.concurrent.CompletableFuture;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weaver;

public class NRAsyncProcessorStart implements AsyncProcessor {
	
	private static boolean isTransformed = false;
	
	private AsyncProcessor delegate = null;
	private String consumer = null;
	
	public NRAsyncProcessorStart(AsyncProcessor processor) {
		delegate = processor;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(dispatcher = true)
	public void process(Exchange exchange) throws Exception {
		if(consumer != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncConsumerStart",consumer,"process-sync");
		}
		Weaver.callOriginal();
	}

	@Override
	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		if(consumer != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncConsumerStart",consumer,"process-async");
		}
		return delegate.process(exchange, callback);
	}

	@Override
	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> processAsync(Exchange exchange) {
		if(consumer != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncConsumerStart",consumer,"processAsync");
		}
		return delegate.processAsync(exchange);
	}

	public void setConsumer(String s) {
		consumer = s;
	}
}
