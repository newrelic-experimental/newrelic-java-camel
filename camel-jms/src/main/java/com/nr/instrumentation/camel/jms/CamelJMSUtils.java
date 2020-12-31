package com.nr.instrumentation.camel.jms;

import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;
import java.util.logging.Level;

public class CamelJMSUtils {
  public static boolean distributedTracingEnabled;
  
  static {
    Config config = NewRelic.getAgent().getConfig();
    distributedTracingEnabled = ((Boolean)config.getValue("distributed_tracing.enabled", Boolean.valueOf(false))).booleanValue();
    NewRelic.getAgent().getLogger().log(Level.FINE, "CamelJMSUtilsdistributedTracingEnabled set to {0}", Boolean.valueOf(distributedTracingEnabled));
  }
}
