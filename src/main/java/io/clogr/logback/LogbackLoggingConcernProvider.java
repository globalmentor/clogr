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

import java.util.stream.Stream;

import org.slf4j.helpers.Util;

import ch.qos.logback.core.joran.spi.JoranException;
import io.csar.*;

/**
 * Provides a default Logback-based concern for logging. The default logging concern will be automatically configured, equivalent to a default Logback
 * installation.
 * @author Garret Wilson
 * @see LogbackLoggingConcern
 */
public class LogbackLoggingConcernProvider implements ConcernProvider {

	@Override
	public Stream<Concern> concerns() {
		try {
			return Stream.of(new LogbackLoggingConcern().autoConfig());
		} catch(final JoranException joranException) {
			//react to the error as Logback does in its default initialization
			Util.report("Failed to auto configure default logger context", joranException);
			return Stream.empty();
		}
	}
}
