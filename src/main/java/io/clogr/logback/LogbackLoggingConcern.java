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

package io.clogr.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * A concern for logging configurations backed by Logback.
 * <p>
 * A Logback logging concern is also a {@link LoggerContext} and can be configured as such.
 * </p>
 * @author Garret Wilson
 */
public class LogbackLoggingConcern extends LoggerContext implements LoggerContextLoggingConcern {

	@Override
	public LoggerContext getLoggerContext() {
		return this;
	}

	/**
	 * Automatically configures this logging concern.
	 * <p>
	 * This automatic configuration is equivalent to the configuration performed by a default Logback installation independent of Clogr.
	 * </p>
	 * @return This logging concern; useful for chaining configuration commands.
	 * @throws JoranException if an error occurred during configuration;
	 */
	public LogbackLoggingConcern autoConfig() throws JoranException {
		new ContextInitializer(this).autoConfig();
		return this;
	}

}
