package org.apache.camel.component.jms;

import java.util.Map;

import javax.jms.Message;
import javax.jms.Session;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.camel.jms.JMSHeaders;


@Weave
public abstract class JmsBinding {
  protected Message createJmsMessage(Exception cause, Session session) {
    Message msg = (Message)Weaver.callOriginal();
    JMSHeaders headers = new JMSHeaders(msg);
    NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
    return msg;
  }
  
  protected Message createJmsMessage(Exchange exchange, Object body, Map<String, Object> headers, Session session, CamelContext context) {
    Message msg = (Message)Weaver.callOriginal();
    JMSHeaders jmsHeaders = new JMSHeaders(msg);
    NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(jmsHeaders);
    return msg;
  }
  
  protected Message createJmsMessageForType(Exchange exchange, Object body, Session session, CamelContext context, JmsMessageType type) {
    Message msg = (Message)Weaver.callOriginal();
    JMSHeaders jmsHeaders = new JMSHeaders(msg);
    NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(jmsHeaders);
    return msg;
  }
  
  public Message makeJmsMessage(Exchange exchange, org.apache.camel.Message camelMessage, Session session, Exception cause) {
    Message msg = (Message)Weaver.callOriginal();
    JMSHeaders jmsHeaders = new JMSHeaders(msg);
    NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(jmsHeaders);
    return msg;
  }
}
