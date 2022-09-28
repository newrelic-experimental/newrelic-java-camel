package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.processor.ProcessorExchangePair;
import org.apache.camel.util.concurrent.AtomicExchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.processor.MulticastProcessor")
public abstract class MultiCastProcessor_instrumentation {

	@Trace(async=true)
	protected void doProcessParallel(Exchange original, AtomicExchange result, Iterable<ProcessorExchangePair> pairs,boolean streaming, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, original);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);

		Token token = original.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		}
		String classname = getClass().getSimpleName();
		String routeID = original != null ? original.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.addCustomParameter("From Route ID", routeID);
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Processor",classname,"doProcessParallel",routeID});
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	protected boolean doProcessSequential(Exchange original, AtomicExchange result, Iterable<ProcessorExchangePair> pairs, AsyncCallback callback) {
		Token token = original.getProperty(Util.NRTOKENPROPERTY,Token.class);
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, original);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);

		if(token != null) {
			token.link();
		}
		String classname = getClass().getSimpleName();
		String routeID = original != null ? original.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.addCustomParameter("From Route ID", routeID);
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Processor",classname,"doProcessSequential",routeID});
		}
		return Weaver.callOriginal();
	}
	
	protected void updateNewExchange(Exchange exchange, int index, Iterable<ProcessorExchangePair> allPairs, Iterator<ProcessorExchangePair> it) {
		Util.addCompletionIfNeeded(exchange);
		Weaver.callOriginal();
	}

	@Weave(originalName="org.apache.camel.processor.MulticastProcessor$AggregateOnTheFlyTask")
	private static class AggregateOnTheFlyTask {


		private final Exchange original = Weaver.callOriginal();

		@Trace(async=true)
		public void run() {
			Map<String, Object> attributes = new HashMap<String, Object>();
			Util.recordExchange(attributes, original);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
			Token token = original.getProperty(Util.NRTOKENPROPERTY, Token.class);
			if(token != null) {
				token.link();
			}

			Weaver.callOriginal();
		}
	}

	@Weave(originalName="org.apache.camel.processor.MulticastProcessor$ParallelAggregateTimeoutTask")
	private static class ParallelAggregateTimeoutTask {


		private final Exchange original = Weaver.callOriginal();

		@Trace(async=true)
		public void run() {
			Map<String, Object> attributes = new HashMap<String, Object>();
			Util.recordExchange(attributes, original);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
			Token token = original.getProperty(Util.NRTOKENPROPERTY, Token.class);
			if(token != null) {
				token.link();
			}

			Weaver.callOriginal();
		}
	}

	@Weave(originalName="org.apache.camel.processor.MulticastProcessor$ParallelAggregateTask")
	private static class ParallelAggregateTask {

		@NewField
		private Token token = null;

		private ParallelAggregateTask(AtomicExchange result, Exchange subExchange, AtomicInteger aggregated) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				token = t;
			} else if(t != null) {
				t.expire();
			}
		}

		@Trace(async=true)
		public void run() {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}

}