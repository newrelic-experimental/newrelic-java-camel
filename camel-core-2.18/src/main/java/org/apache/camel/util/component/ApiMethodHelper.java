package org.apache.camel.util.component;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ApiMethodHelper {

	@Trace
	public static Object invokeMethod(Object proxy, ApiMethod method, Map<String, Object> properties) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ApiMethodHelper","invokeMethod",method.getName());
		return Weaver.callOriginal();
	}
}
