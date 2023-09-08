package org.apache.camel.component.jms;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.camel.jms.CamelHeaders;

@Weave
public abstract class JmsProducer {
	
	@Trace
	protected boolean processInOnly(Exchange exchange, AsyncCallback callback) {
		Message msg = exchange.getIn();
		if (msg != null) {
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(new CamelHeaders(msg));
		}
		return Weaver.callOriginal();
	}

	@Trace
	protected boolean processInOut(Exchange exchange, AsyncCallback callback) {
		Message msg = exchange.getIn();
		if (msg != null) {
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(new CamelHeaders(msg));
		}
		return Weaver.callOriginal();
	}
}
