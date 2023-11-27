package org.apache.camel.component.kafka.consumer.support;

import org.apache.camel.Exchange;
import org.apache.camel.spi.ExceptionHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.ConsumerRecordHeaders;

@Weave
public abstract class KafkaRecordProcessor {

	@Trace(dispatcher = true)
	public ProcessResult processExchange(Exchange exchange, TopicPartition partition, boolean partitionHasNext, boolean recordHasNext, ConsumerRecord<Object, Object> record, ProcessResult lastResult,
            ExceptionHandler exceptionHandler) {
		ConsumerRecordHeaders headers = new ConsumerRecordHeaders(record);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Kafka, headers);
		return Weaver.callOriginal();
	}
	
	@Weave
	public static final class ProcessResult {
		
	}
}
