package com.newrelic.instrumentation.labs.apache.camel.processors;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import com.newrelic.agent.instrumentation.ClassTransformerService;
import com.newrelic.agent.instrumentation.context.InstrumentationContextManager;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.api.agent.NewRelic;

public class CamelCorePremain {
	
	private static ExecutorService executor = null;
	
	public static void premain(String s, Instrumentation inst) {
		initialize();
	}

	public static void initialize() {
		ClassTransformerService classTransformerService = ServiceFactory.getClassTransformerService();
		if(classTransformerService != null) {
			InstrumentationContextManager contextMgr = classTransformerService.getContextManager();

			if(contextMgr != null) {
				CamelClassTransformer classTransformer = new CamelClassTransformer();
				NewRelic.getAgent().getLogger().log(Level.FINE, "Constructed CamelClassTransformer: {0}, matcher: {1}", classTransformer, classTransformer.getMatcher());
				contextMgr.addContextClassTransformer(classTransformer.getMatcher(), classTransformer);
			} else {
				NewRelic.getAgent().getLogger().log(Level.FINE, "Could not load matcher because ClassTransformerService is null");
				startExecutor();
			}
		} else {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Could not load matcher because InstrumentationContextManager is null");
			startExecutor();
		}
		
	}
	
	private static void startExecutor() {
		executor = Executors.newSingleThreadExecutor();
		RunCheck runCheck = new RunCheck();
		executor.submit(runCheck);
		NewRelic.getAgent().getLogger().log(Level.FINE, "Submit RunCheck to executor");		
	}

	private static void shutdownExecutor() {
		if (executor != null) {
			executor.shutdown();
			NewRelic.getAgent().getLogger().log(Level.FINE, "CamelPremain executor has shut down");
		}
	}


	private static class RunCheck implements Runnable {

		@Override
		public void run() {
			boolean done = false;
			while(!done) {
				ClassTransformerService classTransformerService = ServiceFactory.getClassTransformerService();
				if(classTransformerService != null) {
					InstrumentationContextManager contextMgr = classTransformerService.getContextManager();

					if(contextMgr != null) {
						CamelClassTransformer classTransformer = new CamelClassTransformer();
						NewRelic.getAgent().getLogger().log(Level.FINE, "Constructed CamelClassTransformer: {0}, matcher: {1}", classTransformer, classTransformer.getMatcher());
						contextMgr.addContextClassTransformer(classTransformer.getMatcher(), classTransformer);
						done = true;
					}
				} else {
					try {
						Thread.sleep(2000L);
					} catch (InterruptedException e) {
					}
				}
			}
			shutdownExecutor();
		}

	}

}
