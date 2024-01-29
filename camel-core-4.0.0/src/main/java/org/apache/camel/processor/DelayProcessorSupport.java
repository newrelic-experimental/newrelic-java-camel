package org.apache.camel.processor;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class DelayProcessorSupport {

	@Weave
	private static final class ProcessCall {
		
		@NewField
		private Token token = null;
		
		@SuppressWarnings("unused")
		ProcessCall(Exchange exchange, AsyncCallback callback) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				token = t;
			} else if(t != null) {
				t.expire();
				t = null;
			}
			
		}
		
		@Trace(async = true)
		public void run() {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}
}
