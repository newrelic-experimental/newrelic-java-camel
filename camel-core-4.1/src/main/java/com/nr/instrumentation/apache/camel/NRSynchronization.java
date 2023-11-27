package com.nr.instrumentation.apache.camel;

import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

import com.newrelic.api.agent.NewRelic;

public class NRSynchronization implements Synchronization {
	
	public NRSynchronization() {
	}

	@Override
	public void onComplete(Exchange exchange) {
	}

	@Override
	public void onFailure(Exchange exchange) {
		NewRelic.incrementCounter("/Custom/NRSynchronization/Failed");
		if(exchange.isFailed()) {
			Exception e = exchange.getException();
			if(e != null) {
				NewRelic.noticeError(e);
			}
		}
		
	}

}
