package org.apache.camel.component.seda;

import org.apache.camel.Exchange;
import org.apache.camel.support.ExtendedExchangeExtension;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class SedaProducer {

	protected void addToQueue(Exchange exchange, boolean copy) {
		Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
			if(token.isActive()) {
				exchange.setProperty(Util.NRTOKENPROPERTY, token);
			} else {
				token.expire();
			}
		}
		if(exchange instanceof ExtendedExchangeExtension) {
			Util.addCompletionIfNeeded((ExtendedExchangeExtension)exchange);
		}
		Weaver.callOriginal();
	}
}
