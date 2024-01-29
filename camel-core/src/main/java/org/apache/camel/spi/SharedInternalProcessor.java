package org.apache.camel.spi;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Util;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class SharedInternalProcessor {

	@Trace(dispatcher = true)
    public boolean process(Exchange exchange, AsyncCallback originalCallback, AsyncProcessor processor, Processor resultProcessor) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			traced.setMetricName("Custom","Camel","SharedInternalProcessor",getClass().getSimpleName(),"process1",routeID);
		} else {
			traced.setMetricName("Custom","Camel","SharedInternalProcessor",getClass().getSimpleName(),"process1");
		}
    	traced.addCustomAttribute("AsyncProcessor", processor.getClass().getSimpleName());
    	traced.addCustomAttribute("ResultProcessor", resultProcessor.getClass().getSimpleName());
    	return Weaver.callOriginal();
    }

	@Trace(dispatcher = true)
    public void process(Exchange exchange, AsyncProcessor processor, Processor resultProcessor) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			traced.setMetricName("Custom","Camel","SharedInternalProcessor",getClass().getSimpleName(),"process2",routeID);
		} else {
			traced.setMetricName("Custom","Camel","SharedInternalProcessor",getClass().getSimpleName(),"process2");
		}
    	traced.addCustomAttribute("AsyncProcessor", processor.getClass().getSimpleName());
    	traced.addCustomAttribute("ResultProcessor", resultProcessor.getClass().getSimpleName());
    	Weaver.callOriginal();
    }

}
