package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.WeaveIntoAllMethods;
import com.newrelic.api.agent.weaver.WeaveWithAnnotation;

@WeaveWithAnnotation(type = MatchType.Interface, annotationClasses = {"org.apache.camel.Pattern"})
public abstract class Pattern_instrumentation {
  @WeaveWithAnnotation(annotationClasses = {"org.apache.camel.Pattern"})
  @WeaveIntoAllMethods
  @Trace
  private static void instrumentation() {
    StackTraceElement[] traces = Thread.currentThread().getStackTrace();
    StackTraceElement first = traces[1];
    String methodName = first.getMethodName();
    String classname = first.getClassName();
    NewRelic.getAgent().getTracedMethod().setMetricName(new String[] { "Custom", "Camel", "Handler", classname, methodName });
  }
}
