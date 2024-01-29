package org.apache.camel.spi;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class AsyncProcessorAwaitManager {

	@Trace(dispatcher = true)
	public void process(AsyncProcessor processor, Exchange exchange) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncProcessorAwaitManager",getClass().getSimpleName(),"process");
		Weaver.callOriginal();
	}
}
