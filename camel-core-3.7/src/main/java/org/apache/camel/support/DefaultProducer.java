package org.apache.camel.support;

import org.apache.camel.Exchange;
import org.apache.camel.ExtendedExchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type=MatchType.BaseClass)
public abstract class DefaultProducer {

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
		if(exchange instanceof ExtendedExchange) {
			Util.addCompletionIfNeeded((ExtendedExchange)exchange);
		}
		
		return exchange;
	}
	
}
