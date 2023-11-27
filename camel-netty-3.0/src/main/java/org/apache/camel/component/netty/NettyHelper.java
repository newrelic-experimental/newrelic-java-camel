package org.apache.camel.component.netty;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;

import org.apache.camel.Exchange;
import org.slf4j.Logger;

import com.newrelic.api.agent.GenericParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.NewRelicChannelFutureListener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

@Weave
public class NettyHelper {

	
	public static void writeBodyAsync(Logger log, Channel channel, SocketAddress remoteAddress, Object body,
            Exchange exchange, ChannelFutureListener listener) {
		
		InetSocketAddress address = (InetSocketAddress)remoteAddress;
		
		URI uri = URI.create("netty://"+address.getHostName()+":"+address.getPort());
		GenericParameters params = GenericParameters.library("Camel-Netty").uri(uri).procedure("send").build();
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Camel/Netty/Producer");
		segment.reportAsExternal(params);
		listener = new NewRelicChannelFutureListener(listener, segment);
		Weaver.callOriginal();
	}
}
