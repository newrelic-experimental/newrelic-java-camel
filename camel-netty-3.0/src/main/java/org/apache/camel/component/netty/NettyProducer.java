package org.apache.camel.component.netty;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
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

	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new ExchangeHeaders(exchange));
		
		return Weaver.callOriginal();
	}

	@Trace
	public void processWithConnectedChannel(Exchange exchange, BodyReleaseCallback callback, ChannelFuture channelFuture, Object body) {

		Weaver.callOriginal();
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
