package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.WeaveIntoAllMethods;
import com.newrelic.api.agent.weaver.WeaveWithAnnotation;

public abstract class Produce_instrumentation {
	
	@WeaveIntoAllMethods
	@WeaveWithAnnotation(annotationClasses = {"org.apache.camel.Produce"})
	@Trace(dispatcher = true)
	private static void instrumentation() {
		
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		StackTraceElement first = traces[1];
		String methodName = first.getMethodName();
		String classname = first.getClassName();
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","Handler",classname,methodName);
	}

}
