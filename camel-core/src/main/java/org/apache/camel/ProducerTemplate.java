package org.apache.camel;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.camel.spi.Synchronization;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public class ProducerTemplate {

	@Trace(dispatcher = true)
	public Exchange send(Exchange exchange) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-exchange");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-processor");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBody(Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBody");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndHeader(Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeader");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndProperty(Object body, String property, Object propertyValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndProperty");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndHeaders(Object body, Map<String, Object> headers) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeaders");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(String endpointUri, Exchange exchange) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-endpointuri-ex");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(String endpointUri, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-endpointuri-pro");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(String endpointUri, ExchangePattern pattern, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-endpointuri-expat-pro");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(Endpoint endpoint, Exchange exchange) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-endpointuri-expat-pro");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(Endpoint endpoint, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-endpoint-pro");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(Endpoint endpoint, ExchangePattern pattern, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send-endpoint-expat-pro");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange send(Endpoint endpoint, ExchangePattern pattern, Processor processor, Processor resultProcessor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"send");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBody(Endpoint endpoint, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBody");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBody(String endpointUri, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBody");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object sendBody(Endpoint endpoint, ExchangePattern pattern, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBody");
		return Weaver.callOriginal();
	}


	@Trace(dispatcher = true)
	public Object sendBody(String endpointUri, ExchangePattern pattern, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndHeader(String endpointUri, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeader");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndHeader(Endpoint endpoint, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeader");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object sendBodyAndHeader(Endpoint endpoint, ExchangePattern pattern, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object sendBodyAndHeader(String endpoint, ExchangePattern pattern, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndProperty(String endpointUri, Object body, String property, Object propertyValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndProperty");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndProperty(Endpoint endpoint, Object body, String property, Object propertyValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndProperty");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object sendBodyAndProperty(Endpoint endpoint, ExchangePattern pattern, Object body, String property, Object propertyValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndProperty");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object sendBodyAndProperty(
			String endpoint, ExchangePattern pattern, Object body,
			String property, Object propertyValue)
			{
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndProperty");
				return Weaver.callOriginal();
			}

	@Trace(dispatcher = true)
	public void sendBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeaders");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void sendBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeaders");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object sendBodyAndHeaders(
			String endpointUri, ExchangePattern pattern, Object body,
			Map<String, Object> headers)
			{
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeaders");
				return Weaver.callOriginal();
			}

	@Trace(dispatcher = true)
	public Object sendBodyAndHeaders(
			Endpoint endpoint, ExchangePattern pattern, Object body,
			Map<String, Object> headers)
			{
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"sendBodyAndHeaders");
				return Weaver.callOriginal();
			}

	@Trace(dispatcher = true)
	public Exchange request(Endpoint endpoint, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"request");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Exchange request(String endpointUri, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"request");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBody(Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T requestBody(Object body, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBody(Endpoint endpoint, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T requestBody(Endpoint endpoint, Object body, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBody(String endpointUri, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T requestBody(String endpointUri, Object body, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBodyAndHeader(Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBodyAndHeader(Endpoint endpoint, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T requestBodyAndHeader(Endpoint endpoint, Object body, String header, Object headerValue, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBodyAndHeader(String endpointUri, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T requestBodyAndHeader(String endpointUri, Object body, String header, Object headerValue, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T requestBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object requestBodyAndHeaders(Object body, Map<String, Object> headers) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T requestBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"requestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncSend(String endpointUri, Exchange exchange) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncSend");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncSend(String endpointUri, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncSend");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncSendBody(String endpointUri, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncSendBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncRequestBody(String endpointUri, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncRequestBodyAndHeader(String endpointUri, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncRequestBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> CompletableFuture<T> asyncRequestBody(String endpointUri, Object body, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> CompletableFuture<T> asyncRequestBodyAndHeader(
			String endpointUri, Object body, String header, Object headerValue, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> CompletableFuture<T> asyncRequestBodyAndHeaders(
			String endpointUri, Object body, Map<String, Object> headers, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncSend(Endpoint endpoint, Exchange exchange) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncSend");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncSend(Endpoint endpoint, Processor processor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncSend");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncSendBody(Endpoint endpoint, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncSendBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncRequestBody(Endpoint endpoint, Object body) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncRequestBodyAndHeader(Endpoint endpoint, Object body, String header, Object headerValue) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeader");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncRequestBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers){
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> CompletableFuture<T> asyncRequestBody(Endpoint endpoint, Object body, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBody");
		return Weaver.callOriginal();
	}


	@Trace(dispatcher = true)
	public <T> CompletableFuture<T> asyncRequestBodyAndHeader(
			Endpoint endpoint, Object body, String header, Object headerValue, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeader");
		return Weaver.callOriginal();
	}


	@Trace(dispatcher = true)
	public <T> CompletableFuture<T> asyncRequestBodyAndHeaders(
			Endpoint endpoint, Object body, Map<String, Object> headers, Class<T> type) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncRequestBodyAndHeaders");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncCallback(String endpointUri, Exchange exchange, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallback");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncCallback(Endpoint endpoint, Exchange exchange, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallback");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncCallback(String endpointUri, Processor processor, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallback");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> asyncCallback(Endpoint endpoint, Processor processor, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallback");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncCallbackSendBody(String endpointUri, Object body, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallbackSendBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncCallbackSendBody(Endpoint endpoint, Object body, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallbackSendBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncCallbackRequestBody(String endpointUri, Object body, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallbackRequestBody");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public CompletableFuture<Object> asyncCallbackRequestBody(Endpoint endpoint, Object body, Synchronization onCompletion) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","ProducerTemplate",getClass().getSimpleName(),"asyncCallbackRequestBody");
		return Weaver.callOriginal();
	}

}
