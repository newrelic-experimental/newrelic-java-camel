package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelMessageHeaders;

@Weave(type = MatchType.Interface)
public abstract class ConsumerTemplate {

	@Trace(dispatcher = true)
	public Exchange receive(String endpointUri) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receive");
		Exchange exchange = Weaver.callOriginal();
		Message message = exchange.getMessage();
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(String endpointUri, long timeout) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receive");
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(Endpoint endpoint) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receive");
		Exchange exchange = Weaver.callOriginal();
		Message message = exchange.getMessage();
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(Endpoint endpoint, long timeout) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receive");
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receiveNoWait(String endpointUri) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveNoWait");
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receiveNoWait(Endpoint endpoint) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveNoWait");
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace
	public Object receiveBody(String endpointUri) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		return Weaver.callOriginal();
	}
	
	@Trace
    public Object receiveBody(Endpoint endpoint) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		return Weaver.callOriginal();
    }

	@Trace
    public Object receiveBody(String endpointUri, long timeout){ 
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		Object result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("TimedOut", true);
		}
		return result;
    }

	@Trace
    public Object receiveBody(Endpoint endpoint, long timeout) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		Object result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("TimedOut", true);
		}
		return result;
	}

	@Trace
    public Object receiveBodyNoWait(String endpointUri) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBodyNoWait");
		Object result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("NoObjectAvailable", true);
		}
		return result;
	}

	@Trace
    public Object receiveBodyNoWait(Endpoint endpoint) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBodyNoWait");
		Object result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("NoObjectAvailable", true);
		}
		return result;
	}

	@Trace
    public <T> T receiveBody(String endpointUri, Class<T> type) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		return Weaver.callOriginal();
	}

	@Trace
    public <T> T receiveBody(Endpoint endpoint, Class<T> type) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.addCustomAttribute("BodyClass", type.getName());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		return Weaver.callOriginal();
	}

	@Trace
    public <T> T receiveBody(String endpointUri, long timeout, Class<T> type) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.addCustomAttribute("BodyClass", type.getName());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		T result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("TimedOut", true);
		}
		return result;
	}

	@Trace
    public <T> T receiveBody(Endpoint endpoint, long timeout, Class<T> type) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.addCustomAttribute("BodyClass", type.getName());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBody");
		T result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("TimedOut", true);
		}
		return result;
	}

	@Trace
    public <T> T receiveBodyNoWait(String endpointUri, Class<T> type) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpointUri);
		traced.addCustomAttribute("BodyClass", type.getName());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBodyNoWait");
		T result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("TimedOut", true);
		}
		return result;
	}

	@Trace
    public <T> T receiveBodyNoWait(Endpoint endpoint, Class<T> type) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		traced.addCustomAttribute("BodyClass", type.getName());
		traced.setMetricName("Custom","Camel","Consumer",getClass().getSimpleName(),"receiveBodyNoWait");
		T result = Weaver.callOriginal();
		if(result == null) {
			traced.addCustomAttribute("TimedOut", true);
		}
		return result;
	}


}
