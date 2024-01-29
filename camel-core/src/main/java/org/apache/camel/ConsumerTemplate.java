package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class ConsumerTemplate {

	@Trace(dispatcher = true)
    public Exchange receive(String endpointUri) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receive-endpointUri");
    	return Weaver.callOriginal();
    }

	@Trace(dispatcher = true)
    public Exchange receive(Endpoint endpoint) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receive-endpoint");
    	return Weaver.callOriginal();
    }

	@Trace(dispatcher = true)
    public Exchange receive(String endpointUri, long timeout) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receive-endpointUri-to");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Exchange receive(Endpoint endpoint, long timeout) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receive-endpoint-to");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Exchange receiveNoWait(String endpointUri) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveNoWait-endpointUri");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Exchange receiveNoWait(Endpoint endpoint) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveNoWait-endpoint");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Object receiveBody(String endpointUri) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpointuri");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Object receiveBody(Endpoint endpoint) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpoint");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Object receiveBody(String endpointUri, long timeout) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpointuri-to");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Object receiveBody(Endpoint endpoint, long timeout) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpoint-to");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Object receiveBodyNoWait(String endpointUri) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBodyNoWait-endpointuri");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public Object receiveBodyNoWait(Endpoint endpoint) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBodyNoWait-endpoint");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public <T> T receiveBody(String endpointUri, Class<T> type) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpointuri-type");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public <T> T receiveBody(Endpoint endpoint, Class<T> type) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpoint-type");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public <T> T receiveBody(String endpointUri, long timeout, Class<T> type) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpointuri-to-type");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public <T> T receiveBody(Endpoint endpoint, long timeout, Class<T> type) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBody-endpoint-to-type");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public <T> T receiveBodyNoWait(String endpointUri, Class<T> type) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBodyNoWait-endpointuri-type");
    	return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
    public <T> T receiveBodyNoWait(Endpoint endpoint, Class<T> type) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ConsumerTemplate",getClass().getSimpleName(),"receiveBodyNoWait-endpoint-type");
    	return Weaver.callOriginal();
	}

}
