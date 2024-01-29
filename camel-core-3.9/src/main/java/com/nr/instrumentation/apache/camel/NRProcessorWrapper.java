package com.nr.instrumentation.apache.camel;

import org.apache.camel.DelegateProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRProcessorWrapper implements DelegateProcessor {
	
	protected Token token = null;
	private Processor delegate = null;
	protected static boolean isTransformed = false;

	
	public NRProcessorWrapper(Processor d, Token t) {
		token = t;
		delegate = d;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(async = true)
	public void process(Exchange exchange) throws Exception {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		delegate.process(exchange);
	}

	@Override
	public Processor getProcessor() {
		return delegate;
	}

}
