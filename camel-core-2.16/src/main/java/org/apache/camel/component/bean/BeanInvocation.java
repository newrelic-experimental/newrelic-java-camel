package org.apache.camel.component.bean;

import java.lang.reflect.Method;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class BeanInvocation {

	public abstract Method getMethod();
	
	@Trace
	public void invoke(Object pojo, Exchange exchange) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BeanInvocation","invoke",Util.getMethodName(getMethod()));
		Weaver.callOriginal();
	}
}
