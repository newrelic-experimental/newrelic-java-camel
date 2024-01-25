package com.nr.instrumentation.apache.camel;

import java.util.concurrent.CompletableFuture;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRAsyncProcessorWrapper  implements AsyncProcessor  {
	
	private AsyncProcessor delegate = null;
	protected Token token = null;
	protected static boolean isTransformed = false;
	
	
	public NRAsyncProcessorWrapper(AsyncProcessor d, Token t) {
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
		token = t;
		delegate = d;
	}

	@Override
	@Trace(async = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		return delegate.process(exchange,callback);
	}

	@Override
	@Trace(async = true)
	public CompletableFuture<Exchange> processAsync(Exchange exchange) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		return delegate.processAsync(exchange);
	}

	@Trace(async = true)
	public void process(Exchange exchange) throws Exception {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		delegate.process(exchange);
	}

}
