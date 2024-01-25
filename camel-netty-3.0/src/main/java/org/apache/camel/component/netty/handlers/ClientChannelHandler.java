package org.apache.camel.component.netty.handlers;

import org.apache.camel.Exchange;
import org.apache.camel.component.netty.NettyCamelState;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.ExchangeHeaders;

import io.netty.channel.ChannelHandlerContext;

@Weave
public abstract class ClientChannelHandler {

	@Trace(dispatcher = true)
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "ClientChannelHandler", "Custom","ClientChannelHandler",msg.getClass().getSimpleName());
		Weaver.callOriginal();
	}
	
	@SuppressWarnings("unused")
	private NettyCamelState getState(ChannelHandlerContext ctx, Object msg) {
		NettyCamelState state = Weaver.callOriginal();
		Exchange exchange = state.getExchange();
		if(exchange != null) {
			ExchangeHeaders headers = new ExchangeHeaders(exchange);
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, headers);
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		}
		return state;
	}
}
