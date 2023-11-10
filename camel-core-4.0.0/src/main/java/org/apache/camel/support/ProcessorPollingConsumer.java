package org.apache.camel.support;

import java.util.HashMap;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type = MatchType.BaseClass)
public abstract class ProcessorPollingConsumer {

	public abstract Endpoint getEndpoint();
	
	@Trace(dispatcher = true)
	public Exchange receive() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receive");
		Endpoint endPt = getEndpoint();
		if(endPt != null) {
			String endpoint = endPt.getEndpointBaseUri();
			if(endpoint != null && !endpoint.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "PollingConsumer", "ProcessorPollingConsumer",endpoint);
			}
		}
		Exchange result = Weaver.callOriginal();
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, result);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(result));
		return result;
	}

	@Trace(dispatcher = true)
	public Exchange receiveNoWait() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receiveNoWait");
		Exchange result = Weaver.callOriginal();
		if(result != null) {
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(result));
			HashMap<String, Object> attributes = new HashMap<>();
			Util.recordExchange(attributes, result);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
			Endpoint endPt = getEndpoint();
			if(endPt != null) {
				String endpoint = endPt.getEndpointBaseUri();
				if(endpoint != null && !endpoint.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false,  "PollingConsumer","ProcessorPollingConsumer",endpoint);
				}
			}
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return result;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(long timeout) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receive");
		Exchange result = Weaver.callOriginal();
		if(result != null) {
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(result));
			HashMap<String, Object> attributes = new HashMap<>();
			Util.recordExchange(attributes, result);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
			Endpoint endPt = getEndpoint();
			if(endPt != null) {
				String endpoint = endPt.getEndpointBaseUri();
				if(endpoint != null && !endpoint.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "PollingConsumer", "ProcessorPollingConsumer",endpoint);
				}
			}
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return result;
	}
}
