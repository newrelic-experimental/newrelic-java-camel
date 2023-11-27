package com.newrelic.instrumentation.labs.camel.netty;

import org.apache.camel.AsyncCallback;

import com.newrelic.api.agent.Segment;

public class AsyncCallbackWrapper implements AsyncCallback {
	
	private AsyncCallback delegate = null;
	private Segment segment = null;
	
	public AsyncCallbackWrapper(AsyncCallback del) {
		delegate = del;
	}
	
	public void setSegment(Segment seg) {
		segment = seg;
	}
	
	public void ignoreSegment() {
		if(segment != null) {
			segment.ignore();
			segment = null;
		}
	}

	@Override
	public void done(boolean doneSync) {
		if(segment != null) {
			segment.end();
			segment = null;
		}
		if(delegate != null) {
			delegate.done(doneSync);
		}
	}

	@Override
	public void run() {
		delegate.run();
	}

	
}
