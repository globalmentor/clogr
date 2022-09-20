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

package io.clogr.logback;

import static java.util.Objects.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

import javax.annotation.*;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.selector.ContextSelector;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * A concern for logging configurations backed by Logback.
 * @apiNote This logging concern is for manual, programmatic configuration. It is not a default logging concern that delegates to the existing Logback SLF4J
 *          configuration.
 * @apiNote A Logback logging concern is also a {@link LoggerContext} and can be configured as such. However note that as of Logback 1.3.0-alpha4 Logback no
 *          longer supports the {@link ContextSelector} mechanism for logging separation.
 * @author Garret Wilson
 */
public class LogbackLoggingConcern extends LoggerContext implements LoggerContextLoggingConcern {

	@Override
	public LoggerContext getLoggerContext() {
		return this;
	}

	/**
	 * Automatically configures this logging concern.
	 * @apiNote This automatic configuration is equivalent to the configuration performed by a default Logback installation independent of Clogr.
	 * @return This logging concern; useful for chaining configuration commands.
	 * @throws JoranException if an error occurred during configuration;
	 */
	public LogbackLoggingConcern autoConfigure() throws JoranException {
		new ContextInitializer(this).autoConfig();
		return this;
	}

	/**
	 * Configures this logging concern from a Logback configuration file at a specified path.
	 * @apiNote If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration
	 *          must not call {@link #reset()} between each step.
	 * @param path The path to the Logback configuration file.
	 * @return This logging concern; useful for chaining configuration commands.
	 * @throws JoranException if an error occurred during configuration;
	 * @see JoranConfigurator#doConfigure(File)
	 * @see <a href="http://logback.qos.ch/manual/configuration.html">Logback configuration</a>
	 */
	public LogbackLoggingConcern configure(@Nonnull final Path path) throws JoranException {
		return configure(path.toFile());
	}

	/**
	 * Configures this logging concern from a Logback configuration file.
	 * @apiNote If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration
	 *          must not call {@link #reset()} between each step.
	 * @param file The Logback configuration file.
	 * @return This logging concern; useful for chaining configuration commands.
	 * @throws JoranException if an error occurred during configuration;
	 * @see JoranConfigurator#doConfigure(File)
	 * @see <a href="http://logback.qos.ch/manual/configuration.html">Logback configuration</a>
	 */
	public LogbackLoggingConcern configure(@Nonnull final File file) throws JoranException {
		requireNonNull(file);
		final JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(this);
		configurator.doConfigure(file);
		return this;
	}

	/**
	 * Configures this logging concern from a Logback configuration file at a specified URL.
	 * @apiNote If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration
	 *          must not call {@link #reset()} between each step.
	 * @param url The URL to the Logback configuration file.
	 * @return This logging concern; useful for chaining configuration commands.
	 * @throws JoranException if an error occurred during configuration;
	 * @see JoranConfigurator#doConfigure(URL)
	 * @see <a href="http://logback.qos.ch/manual/configuration.html">Logback configuration</a>
	 */
	public LogbackLoggingConcern configure(@Nonnull final URL url) throws JoranException {
		requireNonNull(url);
		final JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(this);
		configurator.doConfigure(url);
		return this;
	}

	/**
	 * Configures this logging concern from a Logback configuration file from a specified input stream.
	 * @apiNote If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration
	 *          must not call {@link #reset()} between each step.
	 * @param inputStream The input stream to the Logback configuration file.
	 * @return This logging concern; useful for chaining configuration commands.
	 * @throws JoranException if an error occurred during configuration;
	 * @see JoranConfigurator#doConfigure(InputStream)
	 * @see <a href="http://logback.qos.ch/manual/configuration.html">Logback configuration</a>
	 */
	public LogbackLoggingConcern configure(@Nonnull final InputStream inputStream) throws JoranException {
		requireNonNull(inputStream);
		final JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(this);
		configurator.doConfigure(inputStream);
		return this;
	}

}
