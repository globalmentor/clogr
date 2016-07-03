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

package io.clogr;

import org.slf4j.Logger;

/**
 * Mixin interface to provide quick-and-easy logging support to a class.
 * <p>
 * A class implementing this interface can simply call the {@link #getLogger()} method to retrieve a logger.
 * </p>
 * @author Garret Wilson
 */
public interface Clogged {

	/**
	 * Returns an appropriate logger for the class.
	 * <p>
	 * The class returned by {@link Object#getClass()} will be used as the context class.
	 * </p>
	 * @return A logger instance to use with the implementing class.
	 * @see Clogr#getLogger(Class)
	 * @see Object#getClass()
	 */
	public default Logger getLogger() {
		return Clogr.getLogger(getClass());
	}

}
