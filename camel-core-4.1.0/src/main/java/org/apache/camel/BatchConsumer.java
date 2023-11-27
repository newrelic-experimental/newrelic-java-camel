package org.apache.camel;

import java.util.Queue;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class BatchConsumer {

	@Trace(dispatcher = true)
	  public int processBatch(Queue<Object> exchanges)  {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Camel",getClass().getName(),"processBatch"});
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("InputQueueSize", exchanges.size());
		int result=Weaver.callOriginal();
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("NumbertOfBatchProcessed", result);
		return result;
	} 
}

