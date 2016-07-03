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

import java.util.*;

import javax.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.csar.*;

/**
 * Csar Logging Registration (Clogr) simplifies SLF4J logger access while providing compartmentalized logging configurations via {@link Csar}.
 * <p>
 * Clogr is not yet another logging facade. Rather Clogr facilitates access to SLF4J loggers; while improving SLF4J logging configuration, allowing various
 * compartmentalized log settings. The {@link LoggingConcern} is accessed using Csar's concern registration mechanism. The primary logging access method
 * {@link Clogr#getLoggingConcern()} will return the logging concern registered with Csar. The default, unconfigured state will still merely delegate the
 * configured SLF4J logger factory, exactly as could be done dynamically directly from SLF4J. The benefit is that multiple logging configurations can separately
 * be compartmentalized using Csar, and code that accesses loggers via Clogr will be routed to use the correct log configuration automatically.
 * </p>
 * <p>
 * Moreover classes needing access to a logger may implement {@link Clogged} for simplified retrieval of an SLF4J logger.
 * </p>
 * @author Garret Wilson
 * @see Csar
 */
public class Clogr {

	/**
	 * Returns the default logging concern.
	 * @return The default logging concern.
	 * @see Csar#getDefaultConcern(Class)
	 */
	public static Optional<LoggingConcern> getDefaultLoggingConcern() {
		return Csar.getDefaultConcern(LoggingConcern.class);
	}

	/**
	 * Sets the default logging concern.
	 * @param loggingConcern The default logging concern to set.
	 * @return The previous concern, or <code>null</code> if there was no previous concern.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 * @see Csar#registerDefaultConcern(Class, Concern)
	 */
	public static Optional<LoggingConcern> setDefaultLoggingConcern(@Nonnull final LoggingConcern loggingConcern) {
		return Csar.registerDefaultConcern(LoggingConcern.class, loggingConcern);
	}

	/**
	 * Returns the configured logging concern for the current context.
	 * <p>
	 * If no logging concern is registered for the current context, and no default logging concern is registered, a default logging concern instance will be
	 * returned that retrieves loggers with the equivalent of calling {@link LoggerFactory#getLogger(Class)}.
	 * </p>
	 * @return The configured logging concern for the current context.
	 * @see Csar#getConcern(Class)
	 * @see LoggingConcern#DEFAULT
	 */
	public static @Nonnull LoggingConcern getLoggingConcern() {
		return Csar.getConcern(LoggingConcern.class).orElse(LoggingConcern.DEFAULT);
	}

	/**
	 * Returns an appropriate logger for the current context.
	 * <p>
	 * This is a convenience method that requests a logger from the current context logging concern.
	 * </p>
	 * @param contextClass The context for which logging is to be performed.
	 * @return A logger instance to use with the given context class in the current context.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @see #getLoggingConcern()
	 * @see LoggingConcern#getLogger(Class)
	 */
	public static @Nonnull Logger getLogger(@Nonnull final Class<?> contextClass) {
		return getLoggingConcern().getLogger(contextClass);
	}

}
