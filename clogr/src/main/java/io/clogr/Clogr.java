/*
 * Copyright © 2016 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.clogr;

import static java.io.OutputStream.*;

import java.io.PrintStream;
import java.util.*;

import javax.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOP_FallbackServiceProvider;
import org.slf4j.spi.SLF4JServiceProvider;

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
	 * @return The default logging concern, if any.
	 * @see Csar#findDefaultConcern(Class)
	 */
	public static Optional<LoggingConcern> findDefaultLoggingConcern() {
		return Csar.findDefaultConcern(LoggingConcern.class);
	}

	/**
	 * Sets the default logging concern.
	 * @param loggingConcern The default logging concern to set.
	 * @return The previous concern, if any.
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
		return Csar.findConcern(LoggingConcern.class).orElse(LoggingConcern.DEFAULT);
	}

	/**
	 * Returns an appropriate logger for the current context.
	 * @apiNote This is a convenience method that requests a logger from the current context logging concern.
	 * @param contextClass The context for which logging is to be performed.
	 * @return A logger instance to use with the given context class in the current context.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @see #getLoggingConcern()
	 * @see LoggingConcern#getLogger(Class)
	 */
	public static @Nonnull Logger getLogger(@Nonnull final Class<?> contextClass) {
		return getLoggingConcern().getLogger(contextClass);
	}

	/**
	 * Sets the preferred system default SLF4J provider to be used. If SLF4J provider initialization has already occurred, this method has no effect.
	 * <p>
	 * This default logging provider will only be used by Clogr logging concerns such as {@link LoggingConcern#DEFAULT} that access SLF4J directly, and by code
	 * that accesses SLF4J without being aware of Clogr. This setting has no effect on Clogr logging concerns that do not use the system default.
	 * </p>
	 * @apiNote This method is useful for specifying a particular SLF4J logging provider to use if multiple providers are available on the classpath; or
	 *          explicitly setting the logging provider so that the classpath will not be scanned, in order to lower startup times; or supplying a provider to
	 *          use, such as a NOP provider, if no provider is on the classpath at all.
	 * @apiNote This method sets the default SLF4J provider only if it has not already been set. If a different system default logging provider has been
	 *          specified, this method will make no changes but will log a warning. This is useful for a program to set the system default logging provider but
	 *          still allow it to be overridden by explicitly setting the system property externally. To set the default system logging provider invariably, even
	 *          if it has already been set, call {@link #setSystemDefaultLoggingProvider(Class, boolean)} and specify a <code><var>force</var></code> value of
	 *          <code>true</code>.
	 * @implSpec This implementation sets the <code>slf4j.provider</code> system property by delegating to
	 *           {@link #setSystemDefaultLoggingProvider(Class, boolean)} with a <code><var>force</var></code> value of <code>false</code>.
	 * @implNote This implementation immediately "locks in" the new value by initializing SLF4J, so that future calls to this method will have no effect on the
	 *           default provider actually used. This is done to suppress an output to <code>stderr</code> noting that an explicit provider is being used. This
	 *           behavior may be removed when SLF4J discontinues sending such a message to <code>stderr</code>; see
	 *           <a href="https://github.com/qos-ch/slf4j/issues/361">#361: Don't log to stderr when slf4j.provider is used.</a>.
	 * @param loggingProviderClass The class of the default SLF4J logging provider to use.
	 * @see <a href="https://jira.qos.ch/browse/SLF4J-450">SLF4J-450: Allow binding to be explicitly specified</a>
	 */
	public static void setSystemDefaultLoggingProvider(@Nonnull final Class<? extends SLF4JServiceProvider> loggingProviderClass) {
		setSystemDefaultLoggingProvider(loggingProviderClass, false);
	}

	/**
	 * Sets the system default SLF4J provider to be used. If SLF4J provider initialization has already occurred, this method has no effect.
	 * <p>
	 * This default logging provider will only be used by Clogr logging concerns such as {@link LoggingConcern#DEFAULT} that access SLF4J directly, and by code
	 * that accesses SLF4J without being aware of Clogr. This setting has no effect on Clogr logging concerns that do not use the system default.
	 * </p>
	 * @apiNote This method is useful for specifying a particular SLF4J logging provider to use if multiple providers are available on the classpath; or
	 *          explicitly setting the logging provider so that the classpath will not be scanned, in order to lower startup times; or supplying a provider to
	 *          use, such as a NOP provider, if no provider is on the classpath at all.
	 * @implSpec This implementation sets the <code>slf4j.provider</code> system property.
	 * @implNote This implementation immediately "locks in" the new value by initializing SLF4J, so that future calls to this method will have no effect on the
	 *           default provider actually used. This is done to suppress an output to <code>stderr</code> noting that an explicit provider is being used. This
	 *           behavior may be removed when SLF4J discontinues sending such a message to <code>stderr</code>; see
	 *           <a href="https://github.com/qos-ch/slf4j/issues/361">#361: Don't log to stderr when slf4j.provider is used.</a>.
	 * @param loggingProviderClass The class of the default SLF4J logging provider to use.
	 * @param force <code>true</code> if the preferred logging provider should override any system property already set; or <code>false</code> (preferred) to only
	 *          set the property if it is not set already, which allows even this manual setting to still be overridden by explicitly setting the system property
	 *          externally.
	 * @return <code>true</code> if the system default logging provider was updated or already matched the given provider class; or <code>false</code> if the
	 *         system default logging provider was already set and <code><var>force</var></code> was set to <code>false</code>.
	 * @see <a href="https://jira.qos.ch/browse/SLF4J-450">SLF4J-450: Allow binding to be explicitly specified</a>
	 */
	public static boolean setSystemDefaultLoggingProvider(@Nonnull final Class<? extends SLF4JServiceProvider> loggingProviderClass, final boolean force) {
		final String slf4jProviderSystemProperty = "slf4j.provider"; //TODO switch to official SLF4J constant when available; see https://github.com/qos-ch/slf4j/issues/362
		final String loggingProviderClassName = loggingProviderClass.getName();
		final String currentSystemDefaultLoggingProviderClassName = System.getProperty(slf4jProviderSystemProperty);
		final boolean isChanging = !loggingProviderClassName.equals(currentSystemDefaultLoggingProviderClassName);
		if(currentSystemDefaultLoggingProviderClassName != null && isChanging && !force) {
			getLogger(Clogr.class).atDebug().log("System default logger provider already set to `{}`; skipping request to change to `{}`.",
					currentSystemDefaultLoggingProviderClassName, loggingProviderClass);
			return false;
		}
		if(isChanging) {
			if(currentSystemDefaultLoggingProviderClassName != null && force) {
				getLogger(Clogr.class).atWarn().log("Programmatically overriding system default logging provider already set to `{}` with `{}`.",
						currentSystemDefaultLoggingProviderClassName, loggingProviderClass);
			}
			System.setProperty(slf4jProviderSystemProperty, loggingProviderClassName);
		}
		//retrieve a logger factory solely to lock-in the selected provider and suppress the notification to `stdout`
		//see https://github.com/qos-ch/slf4j/issues/361
		final PrintStream originalSystemErr = System.err;
		try {
			System.setErr(new PrintStream(nullOutputStream()));
			LoggerFactory.getILoggerFactory();
		} finally {
			System.setErr(originalSystemErr);
		}
		return true;
	}

	/**
	 * Sets the SLF4J system default to use a no-op logging provider, essentially suppressing logging output. If SLF4J provider initialization has already
	 * occurred, this method has no effect.
	 * <p>
	 * This default logging provider will only be used by Clogr logging concerns such as {@link LoggingConcern#DEFAULT} that access SLF4J directly, and by code
	 * that accesses SLF4J without being aware of Clogr. This setting has no effect on Clogr logging concerns that do not use the system default.
	 * </p>
	 * @apiNote This method is useful to suppress logging output in unit tests, for example. By default SLF4J will fall back to a no-op logging provider, but
	 *          explicitly requesting a system default no-op logging provider using this method will prevent the warning messages SLF4J usually generates when
	 *          using a fallback provider.
	 * @implSpec This implementation sets the <code>slf4j.provider</code> system property to indicate {@link NOP_FallbackServiceProvider} by delegating to
	 *           {@link #setSystemDefaultLoggingProvider(Class)}.
	 * @implNote This implementation immediately "locks in" the new value by initializing SLF4J, so that future calls to this method will have no effect on the
	 *           default provider actually used. This is done to suppress an output to <code>stderr</code> noting that an explicit provider is being used. This
	 *           behavior may be removed when SLF4J discontinues sending such a message to <code>stderr</code>; see
	 *           <a href="https://github.com/qos-ch/slf4j/issues/361">#361: Don't log to stderr when slf4j.provider is used.</a>.
	 * @see <a href="https://jira.qos.ch/browse/SLF4J-450">SLF4J-450: Allow binding to be explicitly specified</a>
	 */
	public static void setSystemDefaultLoggingProviderNop() {
		setSystemDefaultLoggingProvider(NOP_FallbackServiceProvider.class);
	}

}
