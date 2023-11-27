package org.apache.camel.component.netty;

import java.net.SocketAddress;

import org.slf4j.Logger;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

@Weave
public class NettyHelper {

	@Trace
	public static void writeBodyAsync(
            Logger log, Channel channel, SocketAddress remoteAddress, Object body,
            ChannelFutureListener listener) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Body-Class", body.getClass().getName());
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Channel-Type", channel.getClass().getName());
		Weaver.callOriginal();
	}
}
