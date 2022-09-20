/*
 * Copyright © 2022 GlobalMentor, Inc. <https://www.globalmentor.com/>
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

import org.slf4j.*;

/**
 * A class to serve as the base for implementation-specific logging concerns. It implements default functionality as much as possible, in particular delegating
 * to the {@link LoggerFactory#getILoggerFactory()} lookup. Other functionalities such as setting the log level will be implementation-dependent and implemented
 * by subclasses.
 * @author Garret Wilson
 */
public abstract class BaseLoggingConcern implements LoggingConcern {

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation delegates to {@link LoggerFactory#getILoggerFactory()}.
	 */
	@Override
	public ILoggerFactory getLoggerFactory() {
		return LoggerFactory.getILoggerFactory();
	}

}
