package com.JdoUtil.services;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class JdoService {

	private final JdoServiceFactory factory;

	public JdoService() {
		this(new JdoServiceFactory());
	}

	public JdoService(JdoServiceFactory jdoServiceFactory) {
		this.factory = jdoServiceFactory;
		if (this.factory.getPMF() == null)
			throw new NullPointerException(
					"Persistence Manager instance is null");
	}

	public JdoServiceFactory factory() {
		return factory;
	}

	public PersistenceManagerFactory getPMF() {
		return factory.getPMF();
	}

	public PersistenceManager getPM() {
		return getPMF().getPersistenceManager();
	}

}
