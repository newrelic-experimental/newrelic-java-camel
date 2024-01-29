package org.apache.camel.support;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class DefaultProducer {

	public Exchange createExchange() {
		Exchange exchange = Weaver.callOriginal();
		return exchange;
	}

}
