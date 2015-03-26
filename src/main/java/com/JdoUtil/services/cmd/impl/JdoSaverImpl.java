/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd.impl;

import java.util.Collection;

import javax.jdo.PersistenceManager;

import com.JdoUtil.services.JdoService;
import com.JdoUtil.services.cmd.JdoSaver;

/**
 * The Class JdoSaverImpl.
 */
public class JdoSaverImpl implements JdoSaver {

	/** The jdo service. */
	protected JdoService jdoService;

	/**
	 * Instantiates a new jdo saver impl.
	 *
	 * @param jdoService
	 *            the jdo service
	 */
	public JdoSaverImpl(JdoService jdoService) {
		this.jdoService = jdoService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.saver.JdoSaver#persist(java.lang.Object)
	 */
	public <T> T persist(T entity) throws Exception {

		PersistenceManager pm = null;
		try {

			pm = jdoService.getPM();

			T entityJDO = pm.makePersistent(entity);
			if (entityJDO != null)
				entityJDO = pm.detachCopy(entityJDO);

			return entityJDO;
		} finally {
			jdoService.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.saver.JdoSaver#persistAll(java.util.Collection)
	 */
	public <T> Collection<T> persistAll(Collection<T> entities)
			throws Exception {

		PersistenceManager pm = null;
		try {

			pm = jdoService.getPM();

			Collection<T> updatedEntities = pm.makePersistentAll(entities);

			if (!updatedEntities.isEmpty())
				updatedEntities = pm.detachCopyAll(updatedEntities);

			return updatedEntities;
		} finally {
			jdoService.closePM(pm);
		}
	}

}
