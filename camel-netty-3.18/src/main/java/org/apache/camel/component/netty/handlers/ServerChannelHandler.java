package org.apache.camel.component.netty.handlers;

import java.util.HashMap;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.ExchangeHeaders;
import com.newrelic.instrumentation.labs.camel.netty.Util;

import io.netty.channel.ChannelHandlerContext;

@Weave
public abstract class ServerChannelHandler {

	@Trace(dispatcher = true)
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		Weaver.callOriginal();
	}
	
	@Trace
	private void processAsynchronously(Exchange exchange, ChannelHandlerContext ctx) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		ExchangeHeaders headers = new ExchangeHeaders(exchange);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		Weaver.callOriginal();
	}
	
	@Trace
	private void processSynchronously(Exchange exchange, ChannelHandlerContext ctx) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	protected Exchange createExchange(ChannelHandlerContext ctx, Object message) {
		Exchange result = Weaver.callOriginal();
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new ExchangeHeaders(result));
		return result;
	}
}
