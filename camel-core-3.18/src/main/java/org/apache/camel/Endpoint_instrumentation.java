package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type=MatchType.Interface, originalName = "org.apache.camel.Endpoint")
public abstract class Endpoint_instrumentation {


	public abstract String getEndpointUri();
	public abstract String getEndpointBaseUri();


	public Exchange createExchange() {
		Exchange exchange = Weaver.callOriginal();
		if(Util.isTranscationActive()) {
			CamelHeaders headers = new CamelHeaders(exchange);
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		}
		return exchange;
	}


	public Exchange createExchange(ExchangePattern pattern) {
		Exchange exchange = Weaver.callOriginal();
		if(Util.isTranscationActive()) {
			CamelHeaders headers = new CamelHeaders(exchange);
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		}		
		return exchange;
	}

}
