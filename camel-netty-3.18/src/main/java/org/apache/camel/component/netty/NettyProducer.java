package org.apache.camel.component.netty;

import java.net.URI;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.GenericParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.camel.netty.ExchangeHeaders;

import io.netty.channel.ChannelFuture;

@Weave
public abstract class NettyProducer {

	private NettyConfiguration configuration = Weaver.callOriginal();

	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
//		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new ExchangeHeaders(exchange));
		if(configuration != null) {
			String host = configuration.getHost();
			int port = configuration.getPort();
			URI uri = URI.create("tcp://"+host+":"+port);
			GenericParameters params = GenericParameters.library("Camel-Netty").uri(uri).procedure("process").build();
			Segment segment = NewRelic.getAgent().getTransaction().startSegment("Camel/Netty/Send");
			segment.reportAsExternal(params);
			exchange.setProperty("NRSEGMENT", segment);
		}

		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void processWithConnectedChannel(Exchange exchange, BodyReleaseCallback callback, ChannelFuture channelFuture, Object body) {
		Object obj = exchange.getProperty("NRSEGMENT");
		if(obj != null && obj instanceof Segment) {
			((Segment)obj).end();
		}
		Weaver.callOriginal();
	}


	@SuppressWarnings("unused")
	private boolean processWithBody(final Exchange exchange, Object body, BodyReleaseCallback callback) {
//		ExchangeHeaders headers = new ExchangeHeaders(exchange);
//		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		return Weaver.callOriginal();
	}

	@Weave
	private static class ChannelConnectedListener {
		
		@NewField
		private Token token = null;

		@SuppressWarnings("unused")
		ChannelConnectedListener(Exchange exchange, BodyReleaseCallback callback, Object body) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				token = t;
			} else if(t != null) {
				t.expire();
				t = null;
			}
			
		}
		
		@Trace(async = true)
		public void operationComplete(ChannelFuture future) {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
		
	}


	@Weave
	private static final class BodyReleaseCallback {

	}
}
