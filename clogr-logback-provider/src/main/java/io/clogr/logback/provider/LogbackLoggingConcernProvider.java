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
import static java.util.Collections.*;

import org.slf4j.impl.StaticLoggerBinder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import io.clogr.Clogr;
import io.clogr.LoggingConcern;
import io.clogr.logback.ClogrContextSelector;
import io.clogr.logback.LogbackLoggingConcern;
import io.clogr.logback.LoggerContextDecoratorLoggingConcern;
import io.csar.*;

/**
 * Provides a default Logback-based concern for logging. A logging concern is returned that wraps the default Logback logger context.
 * <p>
 * If this is class is loaded before the Logback before {@link StaticLoggerBinder} is loaded and before {@link ContextSelectorStaticBinder} is initialized, a
 * {@link ClogrContextSelector} will be installed that will use {@link Clogr#getLoggingConcern()} to look up logging contexts.
 * </p>
 * @author Garret Wilson
 * @see LogbackLoggingConcern
 */
public class LogbackLoggingConcernProvider implements ConcernProvider {

	static { //tell Logback to use the ClogrContextSelector for determining logging contexts
		System.setProperty(LOGBACK_CONTEXT_SELECTOR, ClogrContextSelector.class.getName());
		StaticLoggerBinder.getSingleton(); //load the SLF4J logger binder that initiates the Logback binding and installs the context selector 
	}

	@Override
	public Iterable<Concern> getConcerns() {
		//get the default logger context from the context selector (which is hopefully the ClogrContextSelector we asked to be installed)
		final LoggerContext defaultLoggerContext = ContextSelectorStaticBinder.getSingleton().getContextSelector().getDefaultLoggerContext();
		//create a logging concern that wraps the default logger context
		final LoggingConcern defaultLoggingConcern = new LoggerContextDecoratorLoggingConcern(defaultLoggerContext);
		return singleton(defaultLoggingConcern);
	}

}
