package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.Pipeline")
public abstract class Pipeline_instrumentation {
	
	private String id = Weaver.callOriginal();
	
	@Trace
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		
		if(id != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Pipeline","process",id);
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("PipelineID", id);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Pipeline","process");
		}
		return Weaver.callOriginal();
	}
	
	@Weave(originalName="org.apache.camel.processor.Pipeline$PipelineTask")
	private static class PipelineTask {
		
		private Exchange exchange = Weaver.callOriginal();
		
		@Trace(dispatcher=true)
		public void run() {
			if(exchange != null) {
				Map<String, Object> attributes = new HashMap<String, Object>();
				Util.recordExchange(attributes, exchange);
				NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
				String fromRoute = exchange.getFromRouteId();
				if(fromRoute == null) fromRoute = "UnknownFromRoute";
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","PipelineTask","run",fromRoute);
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Pipeline", "Custom","PipelineTask","run",fromRoute);
			}
			Weaver.callOriginal();
		}
	}
	
}