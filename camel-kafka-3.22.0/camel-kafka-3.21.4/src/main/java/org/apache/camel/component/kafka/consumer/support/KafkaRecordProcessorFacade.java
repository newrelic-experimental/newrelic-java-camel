package org.apache.camel.component.kafka.consumer.support;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.ConsumerRecordHeaders;

@Weave
public abstract class KafkaRecordProcessorFacade {

	@Trace(dispatcher = true)
	private ProcessingResult processRecord(TopicPartition partition, boolean partitionHasNext, boolean recordHasNext, KafkaRecordProcessor kafkaRecordProcessor, ConsumerRecord<Object, Object> record) {
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Kafka, new ConsumerRecordHeaders(record));
		String topic = partition.topic();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Camel-Kafka", "Camel","Kafka","ProcessRecord",topic);
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","KafkaRecordProcessorFacade","processRecord",topic);
		return Weaver.callOriginal();
	}
	
	
}
