package com.nr.instrumentation.apache.camel;

import com.newrelic.api.agent.weaver.WeaveWithAnnotation;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.WeaveIntoAllMethods;
import org.apache.camel.spi.InvokeOnHeader;

public abstract class InvokeOnHeader_instrumentation {
	
	@WeaveIntoAllMethods
	@WeaveWithAnnotation(annotationClasses = { "org.apache.camel.spi.InvokeOnHeader" },type=MatchType.Interface)
	@Trace(dispatcher=true)
	private static void instrumentation() {
		
		String name = null;
		
		InvokeOnHeader invokeOnHeaderAnnotation = Weaver.getClassAnnotation(InvokeOnHeader.class);
		if(invokeOnHeaderAnnotation != null) {
			name = invokeOnHeaderAnnotation.value();
		}
		
		if(name == null || name.isEmpty()) {
			Class<?> thisClass = InvokeOnHeader_instrumentation.class;
			name = thisClass.getSimpleName();
		}
		
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		StackTraceElement first = traces[1];
		String methodName = first.getMethodName();
	    Transaction transaction = NewRelic.getAgent().getTransaction();
		if(transaction != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Camel",name,methodName});
			transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, true, "Camel-InvokeOnHeader", new String[] {name,methodName});
		}
	}
}
