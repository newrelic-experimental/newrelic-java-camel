package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRSynchronization;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type=MatchType.Interface)
public abstract class Producer {

	public Exchange createExchange() {
		Exchange exchange = Weaver.callOriginal();
		Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
			if(token.isActive()) {
				exchange.setProperty(Util.NRTOKENPROPERTY, token);
			} else {
				token.expire();
			}
		}
		exchange.addOnCompletion(new NRSynchronization());
		
		return exchange;
	}
	
	
	public Exchange createExchange(ExchangePattern pattern) {
		Exchange exchange = Weaver.callOriginal();
		Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
			if(token.isActive()) {
				exchange.setProperty(Util.NRTOKENPROPERTY, token);
			} else {
				token.expire();
			}
		}
		exchange.addOnCompletion(new NRSynchronization());
		
		return exchange;
	}

}
