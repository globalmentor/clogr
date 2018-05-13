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

import javax.annotation.*;

import ch.qos.logback.classic.LoggerContext;

/**
 * A concern for logging configurations wrapping an existing Logback {@link LoggerContext}.
 * @author Garret Wilson
 */
public class LoggerContextDecoratorLoggingConcern implements LoggerContextLoggingConcern {

	private final LoggerContext loggerContext;

	@Override
	public LoggerContext getLoggerContext() {
		return loggerContext;
	}

	/**
	 * Logger context constructor.
	 * @param loggerContext The Logback logger context this logging concern decorates.
	 */
	public LoggerContextDecoratorLoggingConcern(@Nonnull final LoggerContext loggerContext) {
		this.loggerContext = requireNonNull(loggerContext);
	}

}
