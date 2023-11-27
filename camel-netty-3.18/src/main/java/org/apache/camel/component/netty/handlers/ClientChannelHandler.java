package org.apache.camel.component.netty.handlers;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.netty.NettyCamelState;
import org.apache.camel.component.netty.NettyConstants;
import org.apache.camel.component.netty.NettyProducer;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.ExchangeHeaders;

import io.netty.channel.ChannelHandlerContext;

@Weave
public abstract class ClientChannelHandler {
	
	@NewField
	private Token token = null;
	
	public ClientChannelHandler(NettyProducer producer) {
		
	}

	@Trace(dispatcher = true)
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		
		Weaver.callOriginal();
	}
	
	@SuppressWarnings("unused")
	private NettyCamelState getState(ChannelHandlerContext ctx, Object msg) {
		NettyCamelState returnState = Weaver.callOriginal();
		
		if(returnState != null) {
			Exchange exchange = returnState.getExchange();
			ExchangeHeaders headers = new ExchangeHeaders(exchange);
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, headers);
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return returnState;
	}
	
	protected Message getResponseMessage(Exchange exchange, ChannelHandlerContext ctx, Object message) {
		Message result = Weaver.callOriginal();
		Boolean waiting = exchange.getProperty(NettyConstants.NETTY_CLIENT_CONTINUE, Boolean.class);
		if(result == null || (waiting != null && waiting)) {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return result;
	}
	
	
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
		NewRelic.noticeError(cause);
		Weaver.callOriginal();
	}
}
