package com.newrelic.instrumentation.camel.kafka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class ProducerRecordHeaders implements Headers {
	
	private ProducerRecord<?, ?> producerRecord = null;
	
	public ProducerRecordHeaders(ProducerRecord<?, ?> record) {
		producerRecord = record;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		org.apache.kafka.common.header.Headers headers = producerRecord.headers();
		if(headers != null) {
			Iterable<Header> itemHeaders = headers.headers(name);
			Iterator<Header> iterator = itemHeaders.iterator();
			if(iterator.hasNext()) {
				Header header = iterator.next();
				return new String(header.value());
			}
		}
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		org.apache.kafka.common.header.Headers headers = producerRecord.headers();
		List<String> list = new ArrayList<>();
		if(headers != null) {
			Iterable<Header> itemHeaders = headers.headers(name);
			Iterator<Header> iterator = itemHeaders.iterator();
			while(iterator.hasNext()) {
				Header header = iterator.next();
				list.add(new String(header.value()));
			}
		}
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		producerRecord.headers().add(name, value.getBytes());
	}

	@Override
	public void addHeader(String name, String value) {
		producerRecord.headers().add(name, value.getBytes());
	}

	@Override
	public Collection<String> getHeaderNames() {
		org.apache.kafka.common.header.Headers recordHeaders = producerRecord.headers();
		List<String> list = new ArrayList<>();
		Header[] headerArray = recordHeaders.toArray();
		for(Header header : headerArray) {
			list.add(header.key());
		}
		return list;
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
