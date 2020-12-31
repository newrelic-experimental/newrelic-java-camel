package org.apache.camel.component.bean;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class BeanProcessor {
	
	private String method = Weaver.callOriginal();

	@Trace
	public boolean process(Exchange exchange, AsyncCallback callback) {
		String m = exchange.getIn().getHeader(Exchange.BEAN_METHOD_NAME, method, String.class);
		if(m != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BeanProcessor","process",m});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BeanProcessor","process"});
		}
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}
		return Weaver.callOriginal();
	}
}
