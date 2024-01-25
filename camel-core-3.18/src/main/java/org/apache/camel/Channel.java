package org.apache.camel;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRAsyncProcessorWrapper;
import com.nr.instrumentation.apache.camel.NRProcessorWrapper;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type = MatchType.Interface)
public abstract class Channel {

	public Processor getErrorHandler() {
		Processor processor = Weaver.callOriginal();
		if(processor instanceof AsyncProcessor) {
			NRAsyncProcessorWrapper wrapper = Util.getWrapper((AsyncProcessor)processor);
			if(wrapper != null) {
				processor = wrapper;
			}
		} else {
			NRProcessorWrapper wrapper = Util.getWrapper(processor);
			if(wrapper != null) {
				processor = wrapper;
			}
		}
		return processor;
	}

	public Processor getOutput()  {
		Processor processor = Weaver.callOriginal();
		if(processor instanceof AsyncProcessor) {
			NRAsyncProcessorWrapper wrapper = Util.getWrapper((AsyncProcessor)processor);
			if(wrapper != null) {
				processor = wrapper;
			}
		} else {
			NRProcessorWrapper wrapper = Util.getWrapper(processor);
			if(wrapper != null) {
				processor = wrapper;
			}
		}
		return processor;
	}

	public Processor getNextProcessor()  {
		Processor processor = Weaver.callOriginal();
		if(processor instanceof AsyncProcessor) {
			NRAsyncProcessorWrapper wrapper = Util.getWrapper((AsyncProcessor)processor);
			if(wrapper != null) {
				processor = wrapper;
			}
		} else {
			NRProcessorWrapper wrapper = Util.getWrapper(processor);
			if(wrapper != null) {
				processor = wrapper;
			}
		}
		return processor;
	}

}
