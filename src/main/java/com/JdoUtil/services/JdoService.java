/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.JdoUtil.services.cmd.JdoDeleter;
import com.JdoUtil.services.cmd.JdoLoader;
import com.JdoUtil.services.cmd.JdoSaver;
import com.JdoUtil.services.cmd.impl.JdoCacheLoaderImpl;
import com.JdoUtil.services.cmd.impl.JdoCacheSaverImpl;
import com.JdoUtil.services.cmd.impl.JdoDeleterImpl;
import com.JdoUtil.services.cmd.impl.JdoLoaderImpl;
import com.JdoUtil.services.cmd.impl.JdoSaverImpl;

/**
 * The Class JdoService.
 */
public class JdoService {

	/** The factory. */
	protected final JdoServiceFactory factory;

	/** The cache. */
	protected final boolean cache = true;

	/**
	 * Instantiates a new jdo service.
	 */
	public JdoService() {
		this(new JdoServiceFactory());
	}

	/**
	 * Instantiates a new jdo service.
	 *
	 * @param jdoServiceFactory
	 *            the jdo service factory
	 */
	public JdoService(JdoServiceFactory jdoServiceFactory) {
		this.factory = jdoServiceFactory;
		if (this.factory.getPMF() == null)
			throw new NullPointerException(
					"Persistence Manager instance is null");
	}

	/**
	 * Factory.
	 *
	 * @return the jdo service factory
	 */
	public JdoServiceFactory factory() {
		return factory;
	}

	/**
	 * Loader.
	 *
	 * @return the jdo loader
	 */
	public JdoLoader loader() {
		return loader(cache);
	}

	/**
	 * Loader.
	 *
	 * @param cache
	 *            the cache
	 * @return the jdo loader
	 */
	public JdoLoader loader(boolean cache) {
		return cache ? new JdoCacheLoaderImpl(this) : new JdoLoaderImpl(this);
	}

	/**
	 * Saver.
	 *
	 * @return the jdo saver
	 */
	public JdoSaver saver() {
		return saver(cache);
	}

	/**
	 * Saver.
	 *
	 * @param cache the cache
	 * @return the jdo saver
	 */
	public JdoSaver saver(boolean cache) {
		return cache ? new JdoCacheSaverImpl(this) : new JdoSaverImpl(this);
	}

	/**
	 * Deleter.
	 *
	 * @return the jdo deleter
	 */
	public JdoDeleter deleter() {
		return deleter(cache);
	}

	/**
	 * Deleter.
	 *
	 * @param cache the cache
	 * @return the jdo deleter
	 */
	public JdoDeleter deleter(boolean cache) {
		return cache ? new JdoDeleterImpl(this) : new JdoDeleterImpl(this);
	}

	/**
	 * Gets the pmf.
	 *
	 * @return the pmf
	 */
	public PersistenceManagerFactory getPMF() {
		return factory.getPMF();
	}

	/**
	 * Gets the pm.
	 *
	 * @return the pm
	 */
	public PersistenceManager getPM() {
		return getPMF().getPersistenceManager();
	}

	/**
	 * Close the persistence manager, if its not closed already.
	 *
	 * @param pm
	 *            the pm
	 */
	public void closePM(PersistenceManager pm) {
		if (pm != null && !pm.isClosed())
			pm.close();
	}
}
