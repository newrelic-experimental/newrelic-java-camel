package com.nr.instrumentation.apache.camel;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Route;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRAsyncProcessorWrapper extends NRProcessorWrapper implements AsyncProcessor {
	
	public NRAsyncProcessorWrapper(AsyncProcessor p,Route r) {
		super(p,r);
	}

	@Override
	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		}

		String[] names;
		if(route != null) {
			names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"process",route.getId()};
		} else {
			names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"process"};
			
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(names);
		return ((AsyncProcessor)delegate).process(exchange, callback);
	}

}
