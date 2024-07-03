package com.newrelic.instrumentation.camel.kafka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class ConsumerRecordHeaders implements Headers {

	private ConsumerRecord<?, ?> record = null;

	public ConsumerRecordHeaders(ConsumerRecord<?, ?> rec) {
		record = rec;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		org.apache.kafka.common.header.Headers recordHeaders = record.headers();
		if (recordHeaders != null) {
			Iterable<Header> values = recordHeaders.headers(name);
			Iterator<Header> iterator = values.iterator();
			if(iterator.hasNext()) {
				Header header = iterator.next();
				return new String(header.value());
			}
		}
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> list = new ArrayList<>();
		org.apache.kafka.common.header.Headers recordHeaders = record.headers();
		if (recordHeaders != null) {
			Iterable<Header> values = recordHeaders.headers(name);
			Iterator<Header> iterator = values.iterator();
			while(iterator.hasNext()) {
				Header header = iterator.next();
				String value = new String(header.value());
				list.add(value);
			}
		}
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		record.headers().add(name, value.getBytes());
	}

	@Override
	public void addHeader(String name, String value) {
		record.headers().add(name, value.getBytes());
	}

	@Override
	public Collection<String> getHeaderNames() {
		List<String> list = new ArrayList<>();
		org.apache.kafka.common.header.Headers recordHeaders = record.headers();
		if (recordHeaders != null) {
			Header[] headerArray = recordHeaders.toArray();
			for(Header header : headerArray) {
				list.add(header.key());
			}
		}
		
		return list;
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
