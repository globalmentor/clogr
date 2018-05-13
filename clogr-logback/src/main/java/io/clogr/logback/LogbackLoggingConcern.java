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

import static java.util.Objects.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

import javax.annotation.*;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
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
	public LogbackLoggingConcern autoConfigure() throws JoranException {
		new ContextInitializer(this).autoConfig();
		return this;
	}

	/**
	 * Configures this logging concern from a Logback configuration file at a specified path.
	 * <p>
	 * If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration must not
	 * call {@link #reset()} between each step.
	 * </p>
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
	 * <p>
	 * If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration must not
	 * call {@link #reset()} between each step.
	 * </p>
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
	 * <p>
	 * If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration must not
	 * call {@link #reset()} between each step.
	 * </p>
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
	 * <p>
	 * If this logging concern previously had a configuration that you want to replace, you must first call {@link #reset()}. A multi-step configuration must not
	 * call {@link #reset()} between each step.
	 * </p>
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
