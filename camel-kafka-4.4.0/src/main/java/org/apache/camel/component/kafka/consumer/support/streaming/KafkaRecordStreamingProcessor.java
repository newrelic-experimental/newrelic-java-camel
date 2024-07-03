package org.apache.camel.component.kafka.consumer.support.streaming;

import org.apache.camel.component.kafka.consumer.support.ProcessingResult;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.camel.component.kafka.KafkaConsumer;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageConsumeParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.ConsumerRecordHeaders;

@Weave
abstract class KafkaRecordStreamingProcessor {

	@Trace(dispatcher = true)
	public ProcessingResult processExchange(KafkaConsumer camelKafkaConsumer, TopicPartition topicPartition, boolean partitionHasNext, boolean recordHasNext, ConsumerRecord<Object, Object> consumerRecord) {
		ConsumerRecordHeaders headers = new ConsumerRecordHeaders(consumerRecord);
		MessageConsumeParameters params = MessageConsumeParameters.library("Kafka").destinationType(DestinationType.NAMED_TOPIC).destinationName(topicPartition.topic()).inboundHeaders(headers).build();
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Kafka, headers);
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		return Weaver.callOriginal();
	}
}
