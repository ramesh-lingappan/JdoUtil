package com.JdoUtil.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class JdoServiceFactory {

	private final PersistenceManagerFactory _pmf;

	public JdoServiceFactory() {
		this("transactions-optional");
	}

	public JdoServiceFactory(String configName) {
		this._pmf = JDOHelper.getPersistenceManagerFactory(configName);
	}

	public JdoServiceFactory(PersistenceManagerFactory pmf) {
		this._pmf = pmf;
	}

	public PersistenceManagerFactory getPMF() {
		return _pmf;
	}

	public PersistenceManager getPM() {
		return _pmf.getPersistenceManager();
	}
}
