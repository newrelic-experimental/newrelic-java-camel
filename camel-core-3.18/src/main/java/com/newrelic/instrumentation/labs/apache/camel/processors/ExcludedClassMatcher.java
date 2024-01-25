package com.newrelic.instrumentation.labs.apache.camel.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.newrelic.agent.deps.org.objectweb.asm.ClassReader;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;

public class ExcludedClassMatcher extends ClassMatcher {
	
	private static final List<String> excluded;
	
	static {
		excluded = new ArrayList<>();
		excluded.add("com.nr.instrumentation.apache.camel.NRProcessorStart");
		excluded.add("com.nr.instrumentation.apache.camel.NRAsyncProcessorStart");
		excluded.add("com.nr.instrumentation.apache.camel.NRProcessorWrapper");
		excluded.add("com.nr.instrumentation.apache.camel.NRAsyncProcessorWrapper");
		excluded.add("org.apache.camel.impl.engine.SharedCamelInternalProcessor");
		excluded.add("org.apache.camel.impl.engine.CamelInternalProcessor");
	}

	@Override
	public boolean isMatch(ClassLoader loader, ClassReader cr) {
		
		return !excluded.contains(cr.getClassName());
	}

	@Override
	public boolean isMatch(Class<?> clazz) {
		return !excluded.contains(clazz.getName());
	}

	@Override
	public Collection<String> getClassNames() {
		
		return Collections.emptyList();
	}

}
