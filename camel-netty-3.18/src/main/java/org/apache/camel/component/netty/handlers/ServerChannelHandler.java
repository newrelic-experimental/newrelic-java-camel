package org.apache.camel.component.netty.handlers;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.component.netty.NettyConsumer;
import org.apache.camel.component.netty.NettyEndpoint;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.ExchangeHeaders;
import com.newrelic.instrumentation.labs.camel.netty.Util;

import io.netty.channel.ChannelHandlerContext;

@Weave
public abstract class ServerChannelHandler {
	
	private final NettyConsumer consumer = Weaver.callOriginal();

	@Trace(dispatcher = true)
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("MessageType", msg.getClass().getName());
		if(consumer != null) {
			boolean txNameSet = false;
			NettyEndpoint endpoint = consumer.getEndpoint();
			if(endpoint != null) {
				String uri = endpoint.getEndpointUri();
				if(uri != null && !uri.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Camel-Netty", "Camel-Netty",uri);
					txNameSet = true;
				}
			}
			if(!txNameSet) {
				String routeId = consumer.getRouteId();
				if(routeId != null && !routeId.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Camel-Netty", "Camel-Netty",routeId);
					txNameSet = true;
				}
			}
			if(!txNameSet) {
				Route route = consumer.getRoute();
				if(route != null) {
					String id = route.getId();
					if(id != null && !id.isEmpty()) {
						NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Camel-Netty", "Camel-Netty",id);
						txNameSet = true;
					}
				}
			}
		}
		Weaver.callOriginal();
	}
	
	@Trace
	private void processAsynchronously(Exchange exchange, ChannelHandlerContext ctx) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
//		ExchangeHeaders headers = new ExchangeHeaders(exchange);
//		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
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
