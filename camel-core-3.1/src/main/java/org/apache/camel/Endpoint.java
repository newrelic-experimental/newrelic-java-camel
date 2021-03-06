package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRSynchronization;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type=MatchType.Interface)
public abstract class Endpoint {

	
	public abstract String getEndpointUri();
	
	
	public Exchange createExchange() {
		Exchange exchange = Weaver.callOriginal();
		if(exchange instanceof ExtendedExchange) {
			Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
			if(token == null) {
				token = NewRelic.getAgent().getTransaction().getToken();
				if(token.isActive()) {
					exchange.setProperty(Util.NRTOKENPROPERTY, token);
				} else {
					token.expire();
				}
			}
			ExtendedExchange extended = (ExtendedExchange)exchange;
			extended.addOnCompletion(new NRSynchronization());
			
		}
		
		return exchange;
	}
	
	
	public Exchange createExchange(ExchangePattern pattern) {
		Exchange exchange = Weaver.callOriginal();
		if(exchange instanceof ExtendedExchange) {
			Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
			if(token == null) {
				token = NewRelic.getAgent().getTransaction().getToken();
				if(token.isActive()) {
					exchange.setProperty(Util.NRTOKENPROPERTY, token);
				} else {
					token.expire();
				}
			}
			ExtendedExchange extended = (ExtendedExchange)exchange;
			extended.addOnCompletion(new NRSynchronization());
			
		}
		
		return exchange;
	}

}
