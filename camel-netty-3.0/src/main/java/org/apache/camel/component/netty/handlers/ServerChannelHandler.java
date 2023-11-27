package org.apache.camel.component.netty.handlers;

import java.util.HashMap;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.Util;

import io.netty.channel.ChannelHandlerContext;

@Weave
public abstract class ServerChannelHandler {

	@Trace(dispatcher = true)
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		Weaver.callOriginal();
	}
	
	@Trace
	private void processAsynchronously(Exchange exchange, ChannelHandlerContext ctx, Object message) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	private void processSynchronously(Exchange exchange, ChannelHandlerContext ctx, Object message) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
