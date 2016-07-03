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

import io.csar.*;

/**
 * The concern for retrieving loggers.
 * @author Garret Wilson
 * @see Csar
 */
public interface LoggingConcern extends Concern {

	/**
	 * Default logging concern that essentially does nothing more than delegate to the default SLF4J configuration.
	 * <p>
	 * Retrieving a logger from this logger concern's {@link #getLogger(Class)} is equivalent to calling {@link LoggerFactory#getLogger(Class)}.
	 * </p>
	 * @see LoggerFactory#getILoggerFactory()
	 */
	public static final LoggingConcern DEFAULT = new LoggingConcern() {
		@Override
		public ILoggerFactory getILoggerFactory() {
			return LoggerFactory.getILoggerFactory();
		}
	};

	@Override
	public default Class<LoggingConcern> getConcernType() {
		return LoggingConcern.class;
	}

	/** @return The logger factory configured for this logging concern. */
	public @Nonnull ILoggerFactory getILoggerFactory();

	/**
	 * Returns an appropriate logger for the given context.
	 * <p>
	 * The default implementation delegates to {@link ILoggerFactory#getLogger(String)} using the name of the context class as the logger name.
	 * </p>
	 * @param contextClass The context for which logging is to be performed.
	 * @return A logger instance to use with the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @see #getILoggerFactory()
	 */
	public default @Nonnull Logger getLogger(@Nonnull final Class<?> contextClass) {
		return getILoggerFactory().getLogger(contextClass.getName());
	}

	/**
	 * Retrieves root logger of the underlying logging system.
	 * @return The root logger.
	 * @see #getILoggerFactory()
	 * @see Logger#ROOT_LOGGER_NAME
	 */
	public default @Nonnull Logger getRootLogger() {
		return getILoggerFactory().getLogger(Logger.ROOT_LOGGER_NAME);
	}

}
