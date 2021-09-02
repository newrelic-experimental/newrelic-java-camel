package com.nr.instrumentation.apache.camel;

import org.apache.camel.AsyncCallback;

import com.newrelic.api.agent.Token;

public class NRAsyncCallbackWrapper implements AsyncCallback {
	
	private AsyncCallback delegate = null;
	public Token token = null;
	
	public NRAsyncCallbackWrapper(AsyncCallback d) {
		delegate = d;
	}

	@Override
	public void done(boolean doneSync) {
		if(token != null) {
			token.expire();
			token = null;
		}
		if(delegate != null) {
			delegate.done(doneSync);
		}
	}

}
