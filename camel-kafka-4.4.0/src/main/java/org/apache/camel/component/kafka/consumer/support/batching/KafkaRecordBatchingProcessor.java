package org.apache.camel.component.kafka.consumer.support.batching;

import org.apache.camel.Exchange;
import org.apache.camel.component.kafka.KafkaConsumer;
import org.apache.camel.component.kafka.consumer.support.ProcessingResult;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageConsumeParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.ConsumerRecordHeaders;

@Weave
abstract class KafkaRecordBatchingProcessor {

	@Trace(dispatcher = true)
	public ProcessingResult processExchange(KafkaConsumer camelKafkaConsumer, ConsumerRecords<Object, Object> consumerRecord) {
		return Weaver.callOriginal();
	}

	public Exchange toExchange(KafkaConsumer camelKafkaConsumer, TopicPartition topicPartition, ConsumerRecord<Object, Object> consumerRecord) {
		ConsumerRecordHeaders headers = new ConsumerRecordHeaders(consumerRecord);
		MessageConsumeParameters params = MessageConsumeParameters.library("Kafka").destinationType(DestinationType.NAMED_TOPIC).destinationName(topicPartition.topic()).inboundHeaders(headers).build();
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Kafka, headers);
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		return Weaver.callOriginal();
	}
}
