package org.apache.camel.component.kafka;

import java.util.List;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.kafka.common.header.Header;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.HeaderListWrapper;

@Weave
public abstract class KafkaProducer {
	
	@Trace(dispatcher = true)
	public void process(Exchange exchange) {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		return Weaver.callOriginal();
	}
	
	@SuppressWarnings("unused")
	private List<Header> getPropagatedHeaders(Exchange exchange, Message message) {
		List<Header> headerList = Weaver.callOriginal();
		HeaderListWrapper wrapper = new HeaderListWrapper(headerList);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(wrapper);
		return headerList;
	}
}
