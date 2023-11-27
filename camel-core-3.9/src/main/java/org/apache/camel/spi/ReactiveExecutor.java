package org.apache.camel.spi;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRRunnable;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type = MatchType.Interface)
public abstract class ReactiveExecutor {

	public void schedule(Runnable runnable) {
		NRRunnable wrapper = Util.getRunnable(runnable);
		if(wrapper != null) {
			runnable = wrapper;
		}
		Weaver.callOriginal();
	}

    public void scheduleMain(Runnable runnable) {
		NRRunnable wrapper = Util.getRunnable(runnable);
		if(wrapper != null) {
			runnable = wrapper;
		}
		Weaver.callOriginal();
    }

    public void scheduleSync(Runnable runnable) {
		NRRunnable wrapper = Util.getRunnable(runnable);
		if(wrapper != null) {
			runnable = wrapper;
		}
		Weaver.callOriginal();
    }

    @Trace
    public boolean executeFromQueue() {
    	return Weaver.callOriginal();
    }

}