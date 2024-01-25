package org.apache.camel.component.kafka;

import java.util.List;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.kafka.producer.support.KafkaProducerCallBack;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.HeaderListWrapper;

@Weave
public abstract class KafkaProducer {

	private final KafkaConfiguration configuration = Weaver.callOriginal();
	private final String endpointTopic = Weaver.callOriginal();

	@Trace(dispatcher = true)
	public void process(Exchange exchange) {
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		return Weaver.callOriginal();
	}

	@SuppressWarnings("unused")
	private List<Header> getPropagatedHeaders(Exchange exchange, Message message) {
		List<Header> headerList = Weaver.callOriginal();
		HeaderListWrapper wrapper = new HeaderListWrapper(headerList);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(wrapper);
		return headerList;
	}


	@Trace
	private void processIterableSync(Exchange exchange, Message message) {
		Weaver.callOriginal();
	}

	@Trace(leaf = true)
	private void processSingleMessageSync(Exchange exchange, Message message) {
		String topicName = getTopicName(message);
		MessageProduceParameters params = MessageProduceParameters.library("Kafka").destinationType(DestinationType.NAMED_TOPIC).destinationName(topicName).outboundHeaders(null).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		Weaver.callOriginal();
	}

	@Trace
	private void processIterableAsync(Exchange exchange, KafkaProducerCallBack producerCallBack, Message message) {
		Weaver.callOriginal();
	}

	@Trace(leaf = true)
	private void doSend(Object key, ProducerRecord<Object, Object> record, KafkaProducerCallBack cb) {
		String topic = record.topic();
		if(topic == null || topic.isEmpty()) {
			topic = "UnknownTopic";
		}
		MessageProduceParameters params = MessageProduceParameters.library("Kafka").destinationType(DestinationType.NAMED_TOPIC).destinationName(topic).outboundHeaders(null).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		Weaver.callOriginal();
	}

	private String getTopicName(Message msg) {
		Object overrideTopic = msg.getHeader(KafkaConstants.OVERRIDE_TOPIC);
		if(overrideTopic != null) {
			return overrideTopic.toString();
		}

		String topic = configuration.getTopic();
		if(topic != null) {
			return topic;
		}

		return endpointTopic;
	}
}
