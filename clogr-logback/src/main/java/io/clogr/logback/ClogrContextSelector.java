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

import static java.util.Collections.*;
import static java.util.Objects.*;

import java.util.List;

import javax.annotation.*;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.selector.ContextSelector;
import io.clogr.Clogr;

/**
 * A Logback context selector that uses Clogr to get the current logging context.
 * @apiNote As of Logback 1.3.0-alpha4 Logback no longer supports the {@link ContextSelector} mechanism for logging separation. See
 *          <a href="https://jira.qos.ch/browse/LOGBACK-1196">LOGBACK-1196</a> to track whether it will be reinstated in the future.
 * @implSpec This implementation does not support named contexts and returns an empty list.
 * @author Garret Wilson
 */
public class ClogrContextSelector implements ContextSelector {

	private final LoggerContext defaultLoggerContext;

	/**
	 * Logger context constructor.
	 * @param loggerContext The default logger context.
	 */
	public ClogrContextSelector(@Nonnull final LoggerContext loggerContext) {
		this.defaultLoggerContext = requireNonNull(loggerContext);
	}

	@Override
	public LoggerContext getDefaultLoggerContext() {
		return defaultLoggerContext;
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation uses Clogr to retrieve the current logger context.
	 * @throws ClassCastException if the current logging concern is not an instance of {@link LoggerContextLoggingConcern}.
	 * @see Clogr#getLoggingConcern()
	 */
	@Override
	public LoggerContext getLoggerContext() {
		return ((LoggerContextLoggingConcern)Clogr.getLoggingConcern()).getLoggerContext();
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation does not support named contexts and returns <code>null</code>.
	 */
	@Override
	public LoggerContext detachLoggerContext(final String loggerContextName) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation does not support named contexts and returns an empty list.
	 */
	@Override
	public List<String> getContextNames() {
		return emptyList();
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation does not support named contexts and returns <code>null</code>.
	 */
	public LoggerContext getLoggerContext(final String name) {
		return null;
	}

}
