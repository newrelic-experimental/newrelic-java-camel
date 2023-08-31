package com.nr.instrumentation.camel.jms;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;
import org.apache.camel.Message;

public class OutboundWrapper implements OutboundHeaders {
  private Message msg = null;
  
  public OutboundWrapper(Message m) {
    this.msg = m;
  }
  
  public HeaderType getHeaderType() {
    return HeaderType.MESSAGE;
  }
  
  public void setHeader(String name, String value) {
    if (this.msg != null)
      this.msg.setHeader(name, value); 
  }
}
