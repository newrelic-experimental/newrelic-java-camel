package org.apache.camel.component.jms;

import com.newrelic.api.agent.DistributedTracePayload;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.OutboundHeaders;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.camel.jms.CamelJMSUtils;
import com.nr.instrumentation.camel.jms.OutboundMsgWrapper;
import java.util.Map;
import java.util.logging.Level;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;

@Weave
public abstract class JmsBinding {
  protected Message createJmsMessage(Exception cause, Session session) {
    Message msg = (Message)Weaver.callOriginal();
    OutboundMsgWrapper wrapper = new OutboundMsgWrapper(msg);
    NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders((OutboundHeaders)wrapper);
    if (CamelJMSUtils.distributedTracingEnabled) {
      DistributedTracePayload dtPayload = NewRelic.getAgent().getTransaction().createDistributedTracePayload();
      try {
        String dtText = dtPayload.text();
        if (dtText != null && 
          !msg.propertyExists("newrelic"))
          msg.setStringProperty("newrelic", dtPayload.text()); 
      } catch (JMSException e) {
        NewRelic.getAgent().getLogger().log(Level.FINE, (Throwable)e, "Failed to set distributed tracing payload");
      } 
    } 
    return msg;
  }
  
  protected Message createJmsMessage(Exchange exchange, Object body, Map<String, Object> headers, Session session, CamelContext context) {
    Message msg = (Message)Weaver.callOriginal();
    OutboundMsgWrapper wrapper = new OutboundMsgWrapper(msg);
    NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders((OutboundHeaders)wrapper);
    if (CamelJMSUtils.distributedTracingEnabled) {
      DistributedTracePayload dtPayload = NewRelic.getAgent().getTransaction().createDistributedTracePayload();
      try {
        String dtText = dtPayload.text();
        if (dtText != null && 
          !msg.propertyExists("newrelic"))
          msg.setStringProperty("newrelic", dtPayload.text()); 
      } catch (JMSException e) {
        NewRelic.getAgent().getLogger().log(Level.FINE, (Throwable)e, "Failed to set distributed tracing payload");
      } 
    } 
    return msg;
  }
  
  protected Message createJmsMessageForType(Exchange exchange, Object body, Map<String, Object> headers, Session session, CamelContext context, JmsMessageType type) {
    Message msg = (Message)Weaver.callOriginal();
    OutboundMsgWrapper wrapper = new OutboundMsgWrapper(msg);
    NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders((OutboundHeaders)wrapper);
    if (CamelJMSUtils.distributedTracingEnabled) {
      DistributedTracePayload dtPayload = NewRelic.getAgent().getTransaction().createDistributedTracePayload();
      try {
        String dtText = dtPayload.text();
        if (dtText != null && 
          !msg.propertyExists("newrelic"))
          msg.setStringProperty("newrelic", dtPayload.text()); 
      } catch (JMSException e) {
        NewRelic.getAgent().getLogger().log(Level.FINE, (Throwable)e, "Failed to set distributed tracing payload");
      } 
    } 
    return msg;
  }
  
  public Message makeJmsMessage(Exchange exchange, org.apache.camel.Message camelMessage, Session session, Exception cause) {
    Message msg = (Message)Weaver.callOriginal();
    OutboundMsgWrapper wrapper = new OutboundMsgWrapper(msg);
    NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders((OutboundHeaders)wrapper);
    if (CamelJMSUtils.distributedTracingEnabled) {
      DistributedTracePayload dtPayload = NewRelic.getAgent().getTransaction().createDistributedTracePayload();
      try {
        String dtText = dtPayload.text();
        if (dtText != null && 
          !msg.propertyExists("newrelic"))
          msg.setStringProperty("newrelic", dtPayload.text()); 
      } catch (JMSException e) {
        NewRelic.getAgent().getLogger().log(Level.FINE, (Throwable)e, "Failed to set distributed tracing payload");
      } 
    } 
    return msg;
  }
}
