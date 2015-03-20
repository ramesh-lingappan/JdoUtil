/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * The Class JdoService.
 */
public class JdoService {

	/** The factory. */
	private final JdoServiceFactory factory;

	/**
	 * Instantiates a new jdo service.
	 */
	public JdoService() {
		this(new JdoServiceFactory());
	}

	/**
	 * Instantiates a new jdo service.
	 *
	 * @param jdoServiceFactory the jdo service factory
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

}
