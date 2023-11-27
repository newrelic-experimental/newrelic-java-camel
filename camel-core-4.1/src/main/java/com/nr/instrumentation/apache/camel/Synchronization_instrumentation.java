package com.nr.instrumentation.apache.camel;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;


@Weave(type=MatchType.Interface,originalName="org.apache.camel.spi.Synchronization")
public abstract class Synchronization_instrumentation {

		
		@NewField
		private Token token = null;
		
		@WeaveAllConstructors
		public Synchronization_instrumentation() {
			if(token == null) {
				token = NewRelic.getAgent().getTransaction().getToken();
						
			}
		}
		
		@Trace(async=true)
		public void onComplete(Exchange exchange) {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
		
		@Trace(async=true)
		public void onFailure(Exchange exchange) {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	
}
