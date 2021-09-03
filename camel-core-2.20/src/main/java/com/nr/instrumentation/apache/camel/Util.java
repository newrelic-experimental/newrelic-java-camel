package com.nr.instrumentation.apache.camel;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class Util {
   
   public static final String NRTOKENPROPERTY = "newrelic.asynctoken";

   public static String getMethodName(Method m) {
	   if(m == null) return "UnknownClass.UnknownMethod";
	   
	   String classname = m.getDeclaringClass().getSimpleName();
	   String methodName = m.getName();
	   
	   return classname + "." + methodName;
   }
   
   public static void addCompletionIfNeeded(Exchange exchange) {
	   if(exchange == null) return;
	   List<Synchronization> completions = exchange.handoverCompletions();
	   if(completions == null || completions.isEmpty()) {
		   NRSynchronization nrSync = new NRSynchronization();
		   exchange.addOnCompletion(nrSync);
	   } else {
		   boolean hasNR = false;
		   for(int i=0; i<completions.size() && !hasNR; i++) {
			   Synchronization sync = completions.get(i);
			   hasNR = sync instanceof NRSynchronization;
		   }
		   if(!hasNR) {
			   NRSynchronization nrSync = new NRSynchronization();
			   exchange.addOnCompletion(nrSync);
		   }
	   }
	   Token token = NewRelic.getAgent().getTransaction().getToken();
	   if(token != null && token.isActive()) {
		   exchange.setProperty(NRTOKENPROPERTY, token);
	   } else {
		   if(token != null) {
			   token.expire();
			   token = null;
		   }
		   exchange.removeProperty(NRTOKENPROPERTY);
	   }
   }
}