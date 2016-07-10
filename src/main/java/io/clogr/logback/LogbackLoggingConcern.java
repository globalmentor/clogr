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

import javax.annotation.*;

import org.slf4j.*;
import org.slf4j.event.Level;

import ch.qos.logback.classic.LoggerContext;
import io.clogr.LoggingConcern;

/**
 * A concern for logging configurations backed by Logback.
 * <p>
 * A Logback logging concern is also a {@link LoggerContext} and can be configured as such.
 * </p>
 * @author Garret Wilson
 */
public class LogbackLoggingConcern extends LoggerContext implements LoggingConcern {

	@Override
	public ILoggerFactory getLoggerFactory() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This version returns the root logger as a Logback logger.
	 * </p>
	 */
	@Override
	public ch.qos.logback.classic.Logger getRootLogger() {
		return (ch.qos.logback.classic.Logger)LoggingConcern.super.getRootLogger();
	}

	/**
	 * {@inheritDoc}
	 * @throws ClassCastException if the given logger is an an instance of {@link ch.qos.logback.classic.Logger}.
	 */
	@Override
	public void setLogLevel(final Logger logger, final Level level) {
		((ch.qos.logback.classic.Logger)logger).setLevel(toLogbackLevel(level));
	}

	/**
	 * Determines the Logback log level corresponding to the given SLF4J log level.
	 * @param level The SLF4J log level.
	 * @return The equivalent Logback log level.
	 */
	protected static ch.qos.logback.classic.Level toLogbackLevel(@Nonnull final Level level) {
		switch(level) {
			case ERROR:
				return ch.qos.logback.classic.Level.ERROR;
			case WARN:
				return ch.qos.logback.classic.Level.WARN;
			case INFO:
				return ch.qos.logback.classic.Level.INFO;
			case DEBUG:
				return ch.qos.logback.classic.Level.DEBUG;
			case TRACE:
				return ch.qos.logback.classic.Level.TRACE;
			default:
				throw new AssertionError("Unknown log level: " + level);
		}
	}

}
