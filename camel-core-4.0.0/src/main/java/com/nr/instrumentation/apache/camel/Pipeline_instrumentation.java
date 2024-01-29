package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.Pipeline")
public abstract class Pipeline_instrumentation {
	
	private String id = Weaver.callOriginal();
	
	public boolean process(Exchange exchange, AsyncCallback callback) {
		if(id != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("PipelineID", id);
		}
		return Weaver.callOriginal();
	}
	
	@Weave(originalName="org.apache.camel.processor.Pipeline$PipelineTask")
	private static class PipelineTask {
		
		private Exchange exchange = Weaver.callOriginal();
		
		@Trace
		public void run() {
			if(exchange != null) {
				Map<String, Object> attributes = new HashMap<String, Object>();
				Util.recordExchange(attributes, exchange);
				NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
				String fromRoute = exchange.getFromRouteId();
				if(fromRoute == null) fromRoute = "UnknownFromRoute";
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","PipelineTask","run",fromRoute);
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Pipeline", "Custom","PipelineTask","run",fromRoute);
			}
			Weaver.callOriginal();
		}
	}
	
}