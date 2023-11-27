package org.apache.camel.spi;

import java.util.HashMap;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRAsyncProcessorWrapper;
import com.nr.instrumentation.apache.camel.NRProcessorWrapper;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type = MatchType.Interface)
public abstract class SharedInternalProcessor {

	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback originalCallback, AsyncProcessor processor, Processor resultProcessor) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		NRAsyncProcessorWrapper wrapper = new NRAsyncProcessorWrapper(processor, null);
		processor = wrapper;
		if(resultProcessor != null) {
			NRProcessorWrapper pWrapper = new NRProcessorWrapper(resultProcessor, null);
			resultProcessor = pWrapper;
		}
		return Weaver.callOriginal();
	}
}
