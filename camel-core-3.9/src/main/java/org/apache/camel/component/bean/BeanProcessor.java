package org.apache.camel.component.bean;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class BeanProcessor {
	
	public abstract String getMethod();

	public boolean process(Exchange exchange, AsyncCallback callback) {
		String m = exchange.getIn().getHeader(Exchange.BEAN_METHOD_NAME, getMethod(), String.class);
		if(m != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BeanProcessor","process",m});
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Bean_Method", m);
		}
		return Weaver.callOriginal();
	}
}
