package org.apache.camel.spi;

import org.apache.camel.LoggingLevel;
import org.slf4j.Logger;
import org.slf4j.Marker;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class CamelLogger {

	public static void log(Logger log, LoggingLevel level, String message, Throwable th) {

		if(th != null) {
			if(message != null  && !message.isEmpty()) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("Message", message);

				NewRelic.noticeError(th, params);

			} else {
				NewRelic.noticeError(th);
			}
		}

		Weaver.callOriginal();
	}


	public static void log(Logger log, LoggingLevel level, Marker marker, String message, Throwable th) {

		if(th != null) {
			if(message != null  && !message.isEmpty()) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("Message", message);

				NewRelic.noticeError(th, params);

			} else {
				NewRelic.noticeError(th);
			}
		}

		Weaver.callOriginal();
	}

}
