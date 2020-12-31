package org.apache.camel.spi;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRRunnable;

@Weave(type=MatchType.Interface)
public class ReactiveExecutor {
	
	public void schedule(Runnable runnable) {
		if(!(runnable instanceof NRRunnable)) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				runnable = new NRRunnable(runnable,t);
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		Weaver.callOriginal();
	}

}
