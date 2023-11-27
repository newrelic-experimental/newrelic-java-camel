package org.apache.camel.support;

import org.apache.camel.Exchange;
import org.apache.camel.ExtendedExchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type=MatchType.BaseClass)
public abstract class DefaultProducer {

	public Exchange createExchange() {
		Exchange exchange = Weaver.callOriginal();
		CamelHeaders headers = new CamelHeaders(exchange);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		if(exchange instanceof ExtendedExchange) {
			Util.addCompletionIfNeeded((ExtendedExchange)exchange);
		}
		
		return exchange;
	}
	
}
