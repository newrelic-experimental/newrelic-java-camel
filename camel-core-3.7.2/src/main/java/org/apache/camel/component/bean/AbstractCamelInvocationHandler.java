package org.apache.camel.component.bean;

import java.lang.reflect.Method;

import org.apache.camel.ExchangePattern;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class AbstractCamelInvocationHandler {

	@Trace
	public Object doInvokeProxy(Object proxy, Method method, Object[] args) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Camel","CamelInvocationHandler",getClass().getSimpleName(),"doProxyInvoke",method.getDeclaringClass().getSimpleName(),method.getName()});
		return Weaver.callOriginal();
	}
	
	@Trace
	protected Object invokeProxy(Method method, ExchangePattern pattern, Object[] args, boolean binding) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Camel","CamelInvocationHandler",getClass().getSimpleName(),"invokeProxy",method.getDeclaringClass().getSimpleName(),method.getName()});
		return Weaver.callOriginal();
	}
	
	@Trace
	protected Object invokeWithBody(Method method, Object body, ExchangePattern pattern) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Camel","CamelInvocationHandler",getClass().getSimpleName(),"invokeWithBody",method.getDeclaringClass().getSimpleName(),method.getName()});
		return Weaver.callOriginal();
	}
}
