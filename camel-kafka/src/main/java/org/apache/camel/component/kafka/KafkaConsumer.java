package org.apache.camel.component.kafka;

import org.apache.camel.Exchange;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.camel.kafka.ConsumerRecordHeaders;

@Weave
public class KafkaConsumer {

	@SuppressWarnings("unused")
	private void propagateHeaders(ConsumerRecord<Object, Object> record, Exchange exchange,
			KafkaConfiguration kafkaConfiguration) {
		ConsumerRecordHeaders headers = new ConsumerRecordHeaders(record);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Kafka, headers);
		Weaver.callOriginal();
	}
}
