package com.newrelic.instrumentation.labs.camel.netty;

import com.newrelic.api.agent.Segment;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class NewRelicChannelFutureListener implements ChannelFutureListener {
	
	private ChannelFutureListener delegate = null;
	private Segment segment = null;
	
	public NewRelicChannelFutureListener(ChannelFutureListener d, Segment s) {
		delegate = d;
		segment = s;
	}

	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		if(segment != null) {
			segment.end();
			segment = null;
		}
		if(delegate != null) {
			delegate.operationComplete(future);
		}
	}

}
