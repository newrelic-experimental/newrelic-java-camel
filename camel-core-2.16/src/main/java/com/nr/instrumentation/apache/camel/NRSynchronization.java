package com.nr.instrumentation.apache.camel;

import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class NRSynchronization implements Synchronization {
	
	

	@Override
	public void onComplete(Exchange exchange) {
		if(exchange.hasProperties()) {
			Token token = (Token) exchange.removeProperty(Util.NRTOKENPROPERTY);
			if(token != null) {
				token.expire();
			}
		}
	}

	@Override
	public void onFailure(Exchange exchange) {
		if(exchange.hasProperties()) {
			Token token = (Token) exchange.removeProperty(Util.NRTOKENPROPERTY);
			if(token != null) {
				token.expire();
			}
		}
		if(exchange.isFailed()) {
			Exception e = exchange.getException();
			if(e != null) {
				NewRelic.noticeError(e);
			}
		}
		
	}

}
