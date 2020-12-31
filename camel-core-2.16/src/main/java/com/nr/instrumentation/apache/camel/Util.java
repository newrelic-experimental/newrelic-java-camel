package com.nr.instrumentation.apache.camel;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;

public class Util {
   private static Logger logger = NewRelic.getAgent()
      .getLogger();
   private static boolean Production = true;
   private static Level configLogLevel = Level.INFO;
   
   public static final String NRTOKENPROPERTY = "newrelic.asynctoken";

   static {
      try {
         configLogLevel = Level.parse(NewRelic.getAgent()
            .getConfig()
            .getValue("log_level", "INFO")
            .toUpperCase()
            .trim());
         if (configLogLevel.intValue() <= Level.FINE.intValue())
            Production = false;
      } catch (Exception e) {
         log(Level.INFO, "Unable to configure logging level: {0} message: {1} using INFO", NewRelic.getAgent()
            .getConfig()
            .getValue("log_level"), e.getMessage());
      }
   }

   public static void log(Level level, String format, Object... args) {
      if (configLogLevel.intValue() >= level.intValue())
         try {
            logger.log(level, format, args);
         } catch (Exception e) {
            logger.log(level, e, "{0}", e.getMessage());
         }
   }

   public static void trace() {
      if (Production)
         return;
      StackTraceElement[] ste = Thread.currentThread()
         .getStackTrace();
      for (int i = 2; i < ste.length; i++)
         log(Level.FINE, "trace: {0}: {1}", i, ste[i].toString());
   }



   public static void log(Level level, Exception e, String format, Object... args) {
      logger.log(level, e, format, args);
   }
   
   public static String getMethodName(Method m) {
	   if(m == null) return "UnknownClass.UnknownMethod";
	   
	   String classname = m.getDeclaringClass().getSimpleName();
	   String methodName = m.getName();
	   
	   return classname + "." + methodName;
   }
}