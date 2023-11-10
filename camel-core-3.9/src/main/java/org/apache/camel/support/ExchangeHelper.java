package org.apache.camel.support;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;

@Weave
public class ExchangeHelper {

	public static Exchange copyExchangeAndSetCamelContext(Exchange exchange, CamelContext context, boolean handover) {
		Exchange result = Weaver.callOriginal();

		if(handover) {
			CamelHeaders headers = new CamelHeaders(result);
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		}

		return result;
	}

	public static Exchange createCopy(Exchange exchange, boolean preserveExchangeId) {
		Exchange result = Weaver.callOriginal();
		CamelHeaders headers = new CamelHeaders(result);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		return result;
	}

	public static Exchange createCorrelatedCopy(Exchange exchange, boolean handover, boolean useSameMessageId) {
		Exchange result = Weaver.callOriginal();

		if (handover) {
			CamelHeaders headers = new CamelHeaders(result);
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		}
		return result;
	}



}
