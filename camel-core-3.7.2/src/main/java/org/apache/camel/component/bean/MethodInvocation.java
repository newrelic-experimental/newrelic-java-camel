package org.apache.camel.component.bean;

import java.lang.reflect.Method;

import org.apache.camel.AsyncCallback;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type=MatchType.Interface)
public abstract class MethodInvocation {

	public abstract Method getMethod();
	
	@Trace
	public boolean proceed(AsyncCallback callback) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","MethodInvocation",getClass().getSimpleName(),"proceed",Util.getMethodName(getMethod()));
		return Weaver.callOriginal();
	}
}
