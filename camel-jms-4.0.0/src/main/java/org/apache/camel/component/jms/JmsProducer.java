package org.apache.camel.component.jms;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.OutboundHeaders;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.camel.jms.OutboundWrapper;
import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

@Weave
public abstract class JmsProducer {
  @Trace
  protected boolean processInOnly(Exchange exchange, AsyncCallback callback) {
    Message msg = exchange.getIn();
    NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders((OutboundHeaders)new OutboundWrapper(msg));
    return ((Boolean)Weaver.callOriginal()).booleanValue();
  }
  
  @Trace
  protected boolean processInOut(Exchange exchange, AsyncCallback callback) {
    Message msg = exchange.getIn();
    NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders((OutboundHeaders)new OutboundWrapper(msg));
    return ((Boolean)Weaver.callOriginal()).booleanValue();
  }
}
