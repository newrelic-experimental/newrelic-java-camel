package com.nr.instrumentation.apache.camel;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRRunnable implements Runnable {

	private static boolean isTransformed = false;
	
	private Runnable delegate = null;
	private Token token = null;
	
	public NRRunnable(Runnable d, Token t) {
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
		delegate = d;
		token = t;
	}

	@Override
	@Trace(async=true)
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Delegate", delegate.getClass().getName());
		delegate.run();
	}

}
