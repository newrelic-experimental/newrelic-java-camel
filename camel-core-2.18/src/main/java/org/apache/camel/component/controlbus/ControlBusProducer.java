package org.apache.camel.component.controlbus;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.spi.Language;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ControlBusProducer {

	@Trace
	public boolean process(Exchange exchange, AsyncCallback callback) {
		return Weaver.callOriginal();
	}

	@Trace
	protected void processByAction(Exchange exchange) {
		Weaver.callOriginal();
	}
	
	@Trace
	protected void processByLanguage(Exchange exchange, Language language) {
		Weaver.callOriginal();
	}
}
