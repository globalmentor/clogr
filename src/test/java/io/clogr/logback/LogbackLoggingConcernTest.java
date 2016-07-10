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

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import org.junit.*;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.clogr.*;
import io.csar.*;

/**
 * Tests for {@link LogbackLoggingConcern}.
 * @author Garret Wilson
 */
public class LogbackLoggingConcernTest {

	/**
	 * @see Clogr#setDefaultLoggingConcern(io.clogr.LoggingConcern)
	 * @see Clogr#getLogger(Class)
	 * @see Csar#run(Runnable, Concern...)
	 */
	@Test
	public void testCsarRunSingleThread() throws InterruptedException {
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

		Clogr.getLogger(getClass()).info("default");

		Csar.run(() -> {
			Clogr.getLogger(getClass()).info("local");
		}, localLoggingConcern).join();

		assertThat(defaultAppender.list.get(0).getMessage(), is("default"));
		assertThat(localAppender.list.get(0).getMessage(), is("local"));

	}

}
