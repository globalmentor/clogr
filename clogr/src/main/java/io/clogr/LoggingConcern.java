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

package io.clogr;

import javax.annotation.*;

import org.slf4j.*;
import org.slf4j.event.Level;

import io.csar.*;

/**
 * The concern for retrieving loggers.
 * @author Garret Wilson
 * @see Csar
 */
public interface LoggingConcern extends Concern {

	/**
	 * Default logging concern that essentially does nothing more than delegate to the default SLF4J configuration.
	 * @implSpec Retrieving a logger from this logger concern's {@link #getLogger(Class)} is equivalent to calling {@link LoggerFactory#getLogger(Class)}.
	 * @implNote This logging concern does not support setting the log level.
	 * @see LoggerFactory#getILoggerFactory()
	 */
	public static final LoggingConcern DEFAULT = new BaseLoggingConcern() {
		@Override
		public void setLogLevel(final Logger logger, final Level level) {
			throw new UnsupportedOperationException(
					"Default logging concern does not support setting the log level. Include an implementation-specific Clogr dependency.");
		}
	};

	@Override
	public default Class<LoggingConcern> getConcernType() {
		return LoggingConcern.class;
	}

	/** @return The logger factory configured for this logging concern. */
	public @Nonnull ILoggerFactory getLoggerFactory();

	/**
	 * Returns an appropriate logger for the given context.
	 * @implSpec The default implementation delegates to {@link ILoggerFactory#getLogger(String)} using the name of the context class as the logger name.
	 * @param contextClass The context for which logging is to be performed.
	 * @return A logger instance to use with the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @see #getLoggerFactory()
	 */
	public default @Nonnull Logger getLogger(@Nonnull final Class<?> contextClass) {
		return getLoggerFactory().getLogger(contextClass.getName());
	}

	/**
	 * Retrieves root logger of the underlying logging system.
	 * @return The root logger.
	 * @see #getLoggerFactory()
	 * @see Logger#ROOT_LOGGER_NAME
	 */
	public default @Nonnull Logger getRootLogger() {
		return getLoggerFactory().getLogger(Logger.ROOT_LOGGER_NAME);
	}

	/**
	 * Sets the log level of a particular logger.
	 * @param logger The logger for which the log level should be set.
	 * @param level The minimum logging level for which messages should be logged.
	 */
	public void setLogLevel(@Nonnull Logger logger, @Nonnull final Level level);

	/**
	 * Sets the log level of the root logger.
	 * @param level The minimum logging level for which messages should be logged.
	 * @see #getRootLogger()
	 * @see #setLogLevel(Logger, Level)
	 */
	public default void setLogLevel(@Nonnull final Level level) {
		setLogLevel(getRootLogger(), level);
	}

}
