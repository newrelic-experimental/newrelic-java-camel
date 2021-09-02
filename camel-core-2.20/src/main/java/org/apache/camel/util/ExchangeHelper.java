package org.apache.camel.util;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import java.util.function.Predicate;
import org.apache.camel.spi.Synchronization;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class ExchangeHelper {

	
	public static Exchange copyExchangeAndSetCamelContext(Exchange exchange, CamelContext context, boolean handover) {
		Exchange result = Weaver.callOriginal();
		Util.addCompletionIfNeeded(result);
		
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
		
		return result;
	}
}
