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

package io.clogr.logback.provider;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.*;

import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import io.clogr.logback.ClogrContextSelector;

/**
 * Tests of {@link LogbackLoggingConcernProvider}.
 * <p>
 * Importantly this class tests that {@link LogbackLoggingConcernProvider} installs a {@link ClogrContextSelector} for using Clogr to select Logback contexts.
 * </p>
 * @author Garret Wilson
 */
@Disabled("As of Logback 1.3.0-alpha4 Logback no longer supports the `ContextSelector` mechanism for logging separation.")
public class LogbackLoggingConcernProviderTest {

	/**
	 * Tests that {@link LogbackLoggingConcernProvider} is succeeding in installing a {@link ClogrContextSelector} when accessed before {@link StaticLoggerBinder}
	 * is loaded and before {@link ContextSelectorStaticBinder} is initialized.
	 */
	@Test
	public void testClogrContextSelectorInstalled() {
		new LogbackLoggingConcernProvider(); //creating an instance is just to load and initialize the class, which will install the Clogr context selector
		assertThat(ContextSelectorStaticBinder.getSingleton().getContextSelector(), is(instanceOf(ClogrContextSelector.class)));
	}

}
