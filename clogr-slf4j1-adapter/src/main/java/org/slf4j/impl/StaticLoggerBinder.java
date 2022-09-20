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

package org.slf4j.impl;

import org.slf4j.*;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * Clogr <code>StaticLoggerBinder</code> adapter for SLF4J 1.x legacy support.
 * <p>
 * This class acts as a fake stand-in implementation of <code>org.slf4j.impl.StaticLoggerBinder</code> so that SLF4J 2.x may be used even with applications that
 * rely on <code>StaticLoggerBinder</code>. For such applications, simply include this library as a dependency; no further changes are needed.
 * </p>
 * <p>
 * This library may be used standalone to provide legacy SLF4J 1.x support to any application. It does not rely on Clogr; indeed the Clogr API must be added as
 * a separate dependency.
 * </p>
 * @apiNote This library uses the <code>org.slf4j.impl</code> package, which represents a domain controlled by another entity. Normally this is against
 *          convention, but in this case it is necessary, as some legacy access requires that particular class to be present in that particular package.
 *          Moreover this usage will likely break Java module support when used with actual the SLF4J API distribution; however applications that rely on SLF4J
 *          1.x `StaticLoggerBinder` would break the module system anyway. This implementation is being supplied merely as a stop-gap solution until
 *          applications remove their reliance on legacy SLF4J 1.x features.
 * @implSpec This implementation merely delegates to {@link LoggerFactory#getILoggerFactory()}, thereby relying on the {@link java.util.ServiceLoader} mechanism
 *           of SLF4J 2.x.
 * @implNote This implementation was independently created to adhere to the basic API and minimal functional requirements of the original SLF4J 1.x
 *           <code>org.slf4j.impl.StaticLoggerBinder</code> by Ceki Gülcü, Copyright (c) 2004-2011 QOS.ch.
 * @author Garret Wilson
 * @see <a href="https://jira.qos.ch/browse/SLF4J-561">SLF4J-561: SLF4J 1.x to 2.x migration adapter library.</a>
 */
@SuppressWarnings("deprecation") //the whole purpose of this class is to provide a stop-gap implementation of a deprecated feature
public final class StaticLoggerBinder implements LoggerFactoryBinder {

	/** The singleton instance of this class. */
	private static final StaticLoggerBinder INSTANCE = new StaticLoggerBinder();

	/** @return The singleton instance of this class. */
	public static StaticLoggerBinder getSingleton() {
		return INSTANCE;
	}

	/** This class cannot be publicly instantiated. */
	private StaticLoggerBinder() {
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation delegates to {@link LoggerFactory#getILoggerFactory()}.
	 */
	@Override
	public ILoggerFactory getLoggerFactory() {
		return LoggerFactory.getILoggerFactory();
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation returns the name of the class of the logger factor instance returned by {@link #getLoggerFactory()}.
	 */
	@Override
	public String getLoggerFactoryClassStr() {
		return getLoggerFactory().getClass().getName();
	}

}
