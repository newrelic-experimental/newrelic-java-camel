package org.apache.camel.component.kafka.producer.support;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.kafka.common.header.Header;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.HeaderListWrapper;

@Weave
public abstract class PropagatedHeadersProvider {

	private final List<Header> propagatedHeaders = Weaver.callOriginal();
	
	public List<Header> getHeaders(Exchange childExchange, Message childMessage) {
		List<Header> list = Weaver.callOriginal();
		if(propagatedHeaders != null) {
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(new HeaderListWrapper(list));
		}
		return list;
	}
}
