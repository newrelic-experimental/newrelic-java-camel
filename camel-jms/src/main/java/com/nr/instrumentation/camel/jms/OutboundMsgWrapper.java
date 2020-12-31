package com.nr.instrumentation.camel.jms;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;
import javax.jms.JMSException;
import javax.jms.Message;

public class OutboundMsgWrapper implements OutboundHeaders {
  private Message message = null;
  
  public OutboundMsgWrapper(Message msg) {
    this.message = msg;
  }
  
  public HeaderType getHeaderType() {
    return HeaderType.MESSAGE;
  }
  
  public void setHeader(String name, String value) {
    try {
      if (this.message != null && !this.message.propertyExists(name))
        this.message.setStringProperty(name, value); 
    } catch (JMSException jMSException) {}
  }
}
