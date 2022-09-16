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

import static ch.qos.logback.classic.ClassicConstants.LOGBACK_CONTEXT_SELECTOR;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.clogr.*;
import io.csar.*;

/**
 * Tests for {@link ClogrContextSelector}.
 * @author Garret Wilson
 */
@Disabled("As of Logback 1.3.0-alpha4 Logback no longer supports the `ContextSelector` mechanism for logging separation.")
public class ClogrContextSelectorTest {

	/**
	 * Tests that retrieving a logger via SLF4J after {@link ClogrContextSelector} has been installed, will use the same {@link LoggerContext} as would be used if
	 * accessed directly via Clogr using {@link Clogr#getLogger(Class)}.
	 * @see Clogr#setDefaultLoggingConcern(io.clogr.LoggingConcern)
	 * @see LoggerFactory#getLogger(Class)
	 * @see Csar#run(Runnable, Concern...)
	 * @see LogbackLoggingConcernTest#testCsarRunSingleThread()
	 */
	@Test
	public void testCsarRunSingleThread() throws InterruptedException {

		System.setProperty(LOGBACK_CONTEXT_SELECTOR, ClogrContextSelector.class.getName());

		//set up a default logging concern
		final LogbackLoggingConcern defaultLoggingConcern = new LogbackLoggingConcern();
		final ListAppender<ILoggingEvent> defaultAppender = new ListAppender<>();
		defaultAppender.setContext(defaultLoggingConcern);
		defaultAppender.start();
		defaultLoggingConcern.getRootLogger().addAppender(defaultAppender);

		Clogr.setDefaultLoggingConcern(defaultLoggingConcern);

		//set up a local logging concern
		final LogbackLoggingConcern localLoggingConcern = new LogbackLoggingConcern();
		final ListAppender<ILoggingEvent> localAppender = new ListAppender<>();
		localAppender.setContext(localLoggingConcern);
		localAppender.start();
		localLoggingConcern.getRootLogger().addAppender(localAppender);

		LoggerFactory.getLogger(getClass()).info("default");

		Csar.run(() -> {
			LoggerFactory.getLogger(getClass()).info("local");
		}, localLoggingConcern).join();

		assertThat(defaultAppender.list.get(0).getMessage(), is("default"));
		assertThat(localAppender.list.get(0).getMessage(), is("local"));

	}

}
