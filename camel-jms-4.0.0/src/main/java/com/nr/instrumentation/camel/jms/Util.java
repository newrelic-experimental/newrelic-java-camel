package com.nr.instrumentation.camel.jms;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

public class Util {
   
   public static final String NRTOKENPROPERTY = "newrelic.asynctoken";

   public static String getMethodName(Method m) {
	   if(m == null) return "UnknownClass.UnknownMethod";
	   
	   String classname = m.getDeclaringClass().getSimpleName();
	   String methodName = m.getName();
	   
	   return classname + "." + methodName;
   }
   
   public static void recordExchange(Exchange exchange, Map<String, Object> attributes) {
	   recordValue(attributes, "ExchangeId", exchange.getExchangeId());
	   CamelContext context = exchange.getContext();
	  if(context != null) {
		  recordCamelContext(context, attributes);
	  }
	  Endpoint fromEndpt = exchange.getFromEndpoint();
	  if(fromEndpt != null) {
		  recordValue(attributes, "FromEndpoint-Key", fromEndpt.getEndpointKey());
		  recordValue(attributes, "FromEndpoint-URI", fromEndpt.getEndpointUri());
	  }
	  recordValue(attributes, "FromRouteId", exchange.getFromRouteId());
	  Message inMsg = exchange.getIn();
	  if(inMsg != null) {
		  recordValue(attributes, "In-MessageId", inMsg.getMessageId());
	  }
	  
   }
   
   public static void recordCamelContext(CamelContext context, Map<String,Object> attributes) {
	   recordValue(attributes, "CamelContext-Name", context.getName());
	   recordValue(attributes, "CamelContext-ManagementName", context.getManagementName());
   }
   
   public static void recordValue(Map<String,Object> attributes, String key, Object value) {
	   if(key != null && !key.isEmpty() && value != null) {
		   attributes.put(key, value);
	   }
   }
}