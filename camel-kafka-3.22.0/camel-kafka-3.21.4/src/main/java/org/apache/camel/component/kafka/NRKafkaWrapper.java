package org.apache.camel.component.kafka;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class NRKafkaWrapper {
	
	private static boolean isTransformed = false;
	
	public static ThreadLocal<String> currentTopic = new ThreadLocal<>();
	public static ThreadLocal<Boolean> isSingleMessage = new ThreadLocal<>();
	
	public NRKafkaWrapper() {
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}
	
	@Trace
	public void sendMessage() {
		String topic = currentTopic.get();
		if(topic == null) topic = "UnknownTopic";
		currentTopic.remove();
		isSingleMessage.remove();
		MessageProduceParameters params = MessageProduceParameters.library("Kafka").destinationType(DestinationType.NAMED_TOPIC).destinationName(topic).outboundHeaders(null).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
	}

}
