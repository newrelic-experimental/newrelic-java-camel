package org.apache.camel.component.netty.handlers;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.channel.ChannelHandlerContext;

@Weave
public abstract class ClientChannelHandler {

	@Trace(dispatcher = true)
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		Weaver.callOriginal();
	}
}
