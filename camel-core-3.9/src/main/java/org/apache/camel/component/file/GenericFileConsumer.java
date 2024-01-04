package org.apache.camel.component.file;

import java.util.HashMap;
import java.util.Queue;

import org.apache.camel.Processor;
import org.apache.camel.support.ScheduledBatchPollingConsumer;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type = MatchType.BaseClass)
public abstract class GenericFileConsumer<T> extends ScheduledBatchPollingConsumer {

	protected GenericFileEndpoint<T> endpoint = Weaver.callOriginal();


	public GenericFileConsumer(GenericFileEndpoint<T> endpoint, Processor processor, GenericFileOperations<T> operations, GenericFileProcessStrategy<T> processStrategy) {
		super( endpoint, processor);
	}

	@Trace(dispatcher = true)
	public int processBatch(Queue<Object> exchanges)  {
		HashMap<String, Object> attributes = new HashMap<>();
		int numToProcess = exchanges.size();
		if(numToProcess == 0) {
			NewRelic.getAgent().getTransaction().ignore();
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Camel",getClass().getName(),"processBatch"});
			Util.recordValue(attributes, "RouteId", getRouteId());
			Util.recordValue(attributes, "InputQueueSize", numToProcess);		
			String routeId = getRouteId();

			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, true,"CamelFileConsumer", new String[] {"CamelFileConsumer", routeId != null ? getRouteId() : endpoint.toString()});
		}


		int result=Weaver.callOriginal();
		Util.recordValue(attributes, "NumbertOfBatchProcessed", result);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);

		return result;
	} 
}

