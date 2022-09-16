/*
 * Copyright © 2016 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.clogr.logback.provider;

import static ch.qos.logback.classic.ClassicConstants.LOGBACK_CONTEXT_SELECTOR;
import static io.clogr.logback.LoggerContextLoggingConcern.toLogbackLevel;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import ch.qos.logback.classic.selector.ContextSelector;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import io.clogr.BaseLoggingConcern;
import io.clogr.LoggingConcern;
import io.clogr.logback.*;
import io.csar.*;

/**
 * Provides a default Logback-based concern for logging. A logging concern is returned that recognizes the Logback SLF4J implementation.
 * @implNote As of Logback 1.3.0-alpha4 Logback no longer supports the {@link ContextSelector} mechanism for logging separation. See
 *           <a href="https://jira.qos.ch/browse/LOGBACK-1196">LOGBACK-1196</a> to track whether it will be reinstated in the future. This implementation still
 *           supports legacy Logback implementations that recognize configuring a default context selector in order for non-Clogr-aware libraries to use Clogr's
 *           separate configuration of concerns via Csar, although even in those cases it is not guaranteed that the Clogr context selector will be installed.
 *           In all cases however this implementation will provide access to additional Logback functionality such as setting the log level
 * @author Garret Wilson
 * @see LogbackLoggingConcern
 */
public class LogbackLoggingConcernProvider implements ConcernProvider {

	static {
		/*
		 * In earlier versions of Logback, it was possible to tell Logback to use the `ClogrContextSelector` for determining logging contexts.
		 * However as of Logback 1.3.0-alpha4 Logback no longer supports the `ContextSelector` mechanism for logging separation.
		 * Thus currently ContextSelectorStaticBinder is never initialized, `LOGBACK_CONTEXT_SELECTOR` is ignored, and the following has no effect.
		 * See [LOGBACK-1196](https://jira.qos.ch/browse/LOGBACK-1196) to track whether it will be reinstated in the future.
		 */
		System.setProperty(LOGBACK_CONTEXT_SELECTOR, ClogrContextSelector.class.getName());
	}

	@Override
	public Stream<Concern> concerns() {
		final LoggingConcern logbackLoggingConcern;
		//get the current context selector, which if `LOGBACK_CONTEXT_SELECTOR` were followed our `ClogrContextSelector` might have been installed
		final ContextSelector contextSelector = ContextSelectorStaticBinder.getSingleton().getContextSelector();
		if(contextSelector != null) { //if a pre-v1.3.0 Logback is being used, or context selection has been re-enabled
			logbackLoggingConcern = new LoggerContextDecoratorLoggingConcern(contextSelector.getDefaultLoggerContext()); //create a logging concern that wraps the default logger context
		} else {
			logbackLoggingConcern = new BaseLoggingConcern() {
				@Override
				public void setLogLevel(final Logger logger, final Level level) {
					((ch.qos.logback.classic.Logger)logger).setLevel(toLogbackLevel(level));
				}
			};
		}
		return Stream.of(logbackLoggingConcern);
	}

}
