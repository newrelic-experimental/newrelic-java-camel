package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class PollingConsumer {
	
	@Trace(dispatcher = true)
	public Exchange receive() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receive");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange receiveNoWait() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receiveNoWait");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(long timeout) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","PollingConsumer",getClass().getSimpleName(),"receive-to");
		return Weaver.callOriginal();
	}
}
