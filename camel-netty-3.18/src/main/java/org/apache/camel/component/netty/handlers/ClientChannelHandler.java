package org.apache.camel.component.netty.handlers;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.netty.NettyCamelState;
import org.apache.camel.component.netty.NettyProducer;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.ExchangeHeaders;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

@Weave
public abstract class ClientChannelHandler {
	
	@NewField
	private Token token = null;
	
	public ClientChannelHandler(NettyProducer producer) {
		
	}

	@Trace(dispatcher = true)
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		String channelName = "UnknownChannel";
		Channel channel = ctx.channel();
		if(channel != null) {
			ChannelId channelId = channel.id();
			if(channelId != null) {
				channelName = channelId.asShortText();
			}
		}
		
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","Netty","ClientChannelHandler","channelRead");
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("MessageType", msg.getClass().getName());
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Channel",channelName);
		Weaver.callOriginal();
	}
	
	@SuppressWarnings("unused")
	private NettyCamelState getState(ChannelHandlerContext ctx, Object msg) {
		NettyCamelState state = Weaver.callOriginal();
		if (state != null) {
			Exchange exchange = state.getExchange();
			if (exchange != null) {
				ExchangeHeaders headers = new ExchangeHeaders(exchange);
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, headers);
			} else {
				NewRelic.getAgent().getTransaction().ignore();
			} 
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return state;
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
		NewRelic.noticeError(cause);
		Weaver.callOriginal();
	}
	
	@Trace
	protected Message getResponseMessage(Exchange exchange, ChannelHandlerContext ctx, Object message) throws Exception {
		try {
			return Weaver.callOriginal();
		} catch (Exception e) {
			NewRelic.getAgent().getTransaction().ignore();
			throw e;
		}
	}
}
