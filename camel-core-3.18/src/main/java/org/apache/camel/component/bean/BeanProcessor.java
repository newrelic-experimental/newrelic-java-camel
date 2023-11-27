package org.apache.camel.component.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class BeanProcessor {
	
	public abstract String getMethod();

	@Trace(dispatcher=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String m = exchange.getIn().getHeader(Exchange.BEAN_METHOD_NAME, getMethod(), String.class);
		if(m != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BeanProcessor","process",m});
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Bean_Method", m);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BeanProcessor","process"});
		}
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
		return Weaver.callOriginal();
	}
}
