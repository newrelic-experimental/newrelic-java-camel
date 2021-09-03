package com.nr.instrumentation.apache.camel;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.processor.ProcessorExchangePair;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.processor.MulticastProcessor")
public abstract class MultiCastProcessor_instrumentation {

	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}

		return Weaver.callOriginal();
	}

	@Weave(originalName="org.apache.camel.processor.MulticastProcessor$MulticastReactiveTask")
	protected static abstract class MulticastReactiveTask  {

		
		@Trace(async=true)
		public void run() {
//			Token token = original.getProperty(Util.NRTOKENPROPERTY,Token.class);
//			if(token != null) {
//				token.link();
//			}
			
			Weaver.callOriginal();
		}

	}

}
