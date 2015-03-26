/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * A factory for creating JdoService objects.
 */
public class JdoServiceFactory {

	public static final String MEMCACHE_NAMESPACE = "JdoUtilCache";

	/** The _pmf. */
	protected final PersistenceManagerFactory _pmf;

	protected final MCacheService mcache = new MCacheService(MEMCACHE_NAMESPACE);

	/**
	 * Instantiates a new jdo service factory.
	 */
	public JdoServiceFactory() {
		this("transactions-optional");
	}

	/**
	 * Instantiates a new jdo service factory.
	 *
	 * @param configName
	 *            the config name
	 */
	public JdoServiceFactory(String configName) {
		this._pmf = JDOHelper.getPersistenceManagerFactory(configName);
	}

	/**
	 * Instantiates a new jdo service factory.
	 *
	 * @param pmf
	 *            the pmf
	 */
	public JdoServiceFactory(PersistenceManagerFactory pmf) {
		this._pmf = pmf;
	}

	/**
	 * Gets the pmf.
	 *
	 * @return the pmf
	 */
	public PersistenceManagerFactory getPMF() {
		return _pmf;
	}

	/**
	 * Gets the pm.
	 *
	 * @return the pm
	 */
	public PersistenceManager getPM() {
		return _pmf.getPersistenceManager();
	}

	public MCacheService cacheService() {
		return mcache;
	}
}
