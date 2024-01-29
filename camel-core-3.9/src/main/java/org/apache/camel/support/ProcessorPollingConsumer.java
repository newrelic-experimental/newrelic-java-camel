package org.apache.camel.support;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;

@Weave
public abstract class ProcessorPollingConsumer extends PollingConsumerSupport {
	
	 public ProcessorPollingConsumer(Endpoint endpoint, Processor processor) {
	        super(endpoint);
	 }

	@Trace(dispatcher = true)
	public Exchange receive() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receive");
		Exchange exchange =  Weaver.callOriginal();
		if(exchange != null) {
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other,  new CamelHeaders(exchange));
			Endpoint endPt = getEndpoint();
			if(endPt != null) {
				String uri = endPt.getEndpointUri();
				if(uri != null && !uri.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "PollingConsumer", "Camel","ProcessorPollingConsumer",uri);
				}
			}
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}

	@Trace(dispatcher = true)
	public Exchange receiveNoWait() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receiveNoWait");
		Exchange exchange = Weaver.callOriginal();
		if(exchange != null) {
			Endpoint endPt = getEndpoint();
			if(endPt != null) {
				String uri = endPt.getEndpointUri();
				if(uri != null && !uri.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "PollingConsumer", "Camel","ProcessorPollingConsumer",uri);
				}
			}
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other,  new CamelHeaders(exchange));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(long timeout) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receive");
		Exchange exchange = Weaver.callOriginal();
		if(exchange != null) {
			Endpoint endPt = getEndpoint();
			if(endPt != null) {
				String uri = endPt.getEndpointUri();
				if(uri != null && !uri.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "PollingConsumer", "Camel","ProcessorPollingConsumer",uri);
				}
			}
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other,  new CamelHeaders(exchange));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}

}
