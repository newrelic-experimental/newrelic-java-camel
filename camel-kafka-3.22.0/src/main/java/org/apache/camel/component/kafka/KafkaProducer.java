package org.apache.camel.component.kafka;

import java.util.List;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.kafka.producer.support.KafkaProducerCallBack;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.Headers;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.HeaderListWrapper;
import com.newrelic.instrumentation.camel.kafka.ProducerRecordHeaders;

@Weave
public abstract class KafkaProducer {

	@Trace(dispatcher = true)
	public void process(Exchange exchange) {
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		return Weaver.callOriginal();
	}

	@Trace
	private void processIterableSync(Exchange exchange, Message message) {
		Weaver.callOriginal();
	}

	@Trace
	private void processIterableAsync(Exchange exchange, KafkaProducerCallBack producerCallBack, Message message) {
		Weaver.callOriginal();
	}

	@Trace
	private void processSingleMessageSync(Exchange exchange, Message message) {
		NRKafkaWrapper.isSingleMessage.set(true);
		Weaver.callOriginal();
	}

	protected ProducerRecord<Object, Object> createRecord(Exchange exchange, Message message) {
		ProducerRecord<Object, Object> record = Weaver.callOriginal();
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(new ProducerRecordHeaders(record));

		return record;
	}

	public List<Header> getPropagatedHeaders(Exchange exchange, Message message) {
		List<Header> headerList = Weaver.callOriginal();
		HeaderListWrapper wrapper = new HeaderListWrapper(headerList);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(wrapper);

		return headerList;
	}

	@Trace(leaf = true)
	private void doSend(Object key, ProducerRecord<Object, Object> record, KafkaProducerCallBack cb) {
		Headers headers = new ProducerRecordHeaders(record);
		String topic = record.topic();
		if(topic == null || topic.isEmpty()) {
			topic = "UnknownTopic";
		}
		MessageProduceParameters params = MessageProduceParameters.library("Kafka").destinationType(DestinationType.NAMED_TOPIC).destinationName(topic).outboundHeaders(headers).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		Weaver.callOriginal();
	}
	
	@SuppressWarnings("unused")
	private String evaluateTopic(Message message) {
		String topicName = Weaver.callOriginal();
		Boolean isSingle = NRKafkaWrapper.isSingleMessage.get();
		if(isSingle != null && isSingle) {
			NRKafkaWrapper.currentTopic.set(topicName);
		}
		
		return topicName;
	}
}
