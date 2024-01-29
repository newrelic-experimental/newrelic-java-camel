package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.processor.MulticastProcessor")
public abstract class MultiCastProcessor_instrumentation {

	@Weave(originalName="org.apache.camel.processor.MulticastProcessor$MulticastTask", type = MatchType.BaseClass)
	protected static abstract class MulticastTask  {

		
		@Trace
		protected void doDone(Exchange exchange, boolean forceExhaust) {
			Map<String, Object> attributes = new HashMap<String, Object>();
			Util.recordExchange(attributes, exchange);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","MulticastTask",getClass().getSimpleName(),"doDone");
			Weaver.callOriginal();
		}

	}

}
