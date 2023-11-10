package org.apache.camel.spi;

import java.util.HashMap;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.NRProcessorWrapper;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type = MatchType.Interface)
public abstract class ProducerCache {

	@Trace
	public Exchange send(Endpoint endpoint, Exchange exchange, Processor resultProcessor) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Camel","ProducerCache",getClass().getSimpleName(),"send");
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		String baseURI = endpoint.getEndpointBaseUri();
		if(baseURI != null && !baseURI.isEmpty()) {
			attributes.put("EndpointURI", baseURI);
		}
		traced.addCustomAttributes(attributes);
		if(resultProcessor != null && !(resultProcessor instanceof NRProcessorWrapper)) {
			resultProcessor = new NRProcessorWrapper(resultProcessor, null);
		}
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(new CamelHeaders(exchange));
		return Weaver.callOriginal();
	}
}
