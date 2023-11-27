package org.apache.camel.support;

import org.apache.camel.Exchange;

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
		if(Util.isTranscationActive()) {
			CamelHeaders headers = new CamelHeaders(exchange);
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		}
		return exchange;
	}

}
