package com.newrelic.instrumentation.labs.apache.camel.processors;

import com.newrelic.agent.instrumentation.classmatchers.ClassAndMethodMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.InterfaceMatcher;
import com.newrelic.agent.instrumentation.methodmatchers.MethodMatcher;
import com.newrelic.agent.instrumentation.methodmatchers.NameMethodMatcher;

public class ProcessorClassMethodMatcher implements ClassAndMethodMatcher {
	
	private ProcessorClassMatcher classMatcher;
	private NameMethodMatcher methodMatcher;
	private static final String methodName = "process";
	
	public ProcessorClassMethodMatcher() {
		classMatcher = new ProcessorClassMatcher();
		methodMatcher = new NameMethodMatcher(methodName);
	}

	@Override
	public ClassMatcher getClassMatcher() {
		return classMatcher;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}


}
