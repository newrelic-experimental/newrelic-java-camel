package org.apache.camel.support;

import java.util.function.Predicate;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public class ExchangeHelper {

	public static Exchange copyExchangeAndSetCamelContext(Exchange exchange, CamelContext context, boolean handover) {
		Exchange result = Weaver.callOriginal();
		Util.addCompletionIfNeeded(result);
		
		if(handover) {
			Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
			if(token != null) {
				result.setProperty(Util.NRTOKENPROPERTY, token);
				exchange.removeProperty(Util.NRTOKENPROPERTY);
			} else {
				token = NewRelic.getAgent().getTransaction().getToken();
				if(token != null && token.isActive()) {
					result.setProperty(Util.NRTOKENPROPERTY, token);
				} else if(token != null) {
					token.expire();
					token = null;
				}
			}
		}
		
		return result;
	}
	
	public static Exchange createCopy(Exchange exchange, boolean preserveExchangeId) {
		Exchange result = Weaver.callOriginal();
		Util.addCompletionIfNeeded(result);
		return result;
	}
	
	public static Exchange createCorrelatedCopy(Exchange exchange, boolean handover, boolean useSameMessageId, Predicate<Synchronization> filter) {
		Exchange result = Weaver.callOriginal();
		Util.addCompletionIfNeeded(result);
		
		if (handover) {
			Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
			if(token != null) {
				token.expire();
			}
			Util.addNewToken(result);
		}
		return result;
	}
	
	
	
}
