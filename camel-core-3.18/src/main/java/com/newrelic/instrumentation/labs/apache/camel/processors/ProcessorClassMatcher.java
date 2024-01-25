package com.newrelic.instrumentation.labs.apache.camel.processors;

import java.util.Collection;

import com.newrelic.agent.deps.org.objectweb.asm.ClassReader;
import com.newrelic.agent.instrumentation.classmatchers.AndClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.InterfaceMatcher;

public class ProcessorClassMatcher extends ClassMatcher {
	
	private AndClassMatcher matcher = null;
	private static final String interfaceName = "org.apache.camel.Processor";
	
	public ProcessorClassMatcher() {
		ExcludedClassMatcher excluded = new ExcludedClassMatcher();
		InterfaceMatcher interfaceMatcher = new InterfaceMatcher(interfaceName);
		matcher = new AndClassMatcher(excluded,interfaceMatcher);
	}

	@Override
	public boolean isMatch(ClassLoader loader, ClassReader cr) {
		return matcher.isMatch(loader, cr);
	}

	@Override
	public boolean isMatch(Class<?> clazz) {
		return matcher.isMatch(clazz);
	}

	@Override
	public Collection<String> getClassNames() {
		return matcher.getClassNames();
	}

}
