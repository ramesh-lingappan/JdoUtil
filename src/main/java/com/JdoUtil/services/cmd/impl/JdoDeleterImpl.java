/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd.impl;

import java.util.Collection;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.JdoUtil.services.JdoService;
import com.JdoUtil.services.cmd.JdoDeleter;

/**
 * The Class JdoDeleterImpl.
 */
public class JdoDeleterImpl implements JdoDeleter {

	/** The jdo service. */
	protected JdoService jdoService;

	/**
	 * Instantiates a new jdo deleter impl.
	 *
	 * @param jdoService the jdo service
	 */
	public JdoDeleterImpl(JdoService jdoService) {
		this.jdoService = jdoService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(Object entity) {

		PersistenceManager pm = null;
		try {
			pm = jdoService.getPM();
			pm.setCopyOnAttach(true);

			pm.deletePersistent(entity);
			return true;
		} finally {
			jdoService.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#delete(java.lang.Class,
	 * java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.deleter.JdoDeleter#delete(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T> boolean delete(Class<T> clazz, Object key) {

		PersistenceManager pm = null;
		try {
			pm = jdoService.getPM();
			T entity = pm.getObjectById(clazz, key);
			if (entity == null)
				return false;

			pm.deletePersistent(entity);
			return true;
		} catch (JDOObjectNotFoundException e) {
			return false;
		} finally {
			jdoService.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#deleteAll(java.util.Collection)
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.deleter.JdoDeleter#deleteAll(java.util.Collection)
	 */
	@Override
	public boolean deleteAll(Collection<?> entities) {

		PersistenceManager pm = null;
		try {
			pm = jdoService.getPM();
			pm.setCopyOnAttach(true);

			pm.deletePersistentAll(entities);
			return true;
		} finally {
			jdoService.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#deleteByQuery(java.lang.Class,
	 * java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.deleter.JdoDeleter#deleteByQuery(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	public long deleteByQuery(Class<?> clazz, String queryString)
			throws Exception {

		// critical operation, so no delete without filters
		if (queryString == null || queryString.trim().isEmpty())
			return -1;

		PersistenceManager pm = null;
		try {
			pm = jdoService.getPM();
			return pm.newQuery(clazz, queryString).deletePersistentAll();
		} finally {
			jdoService.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#deleteByQuery(java.lang.Class,
	 * java.lang.String, java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.deleter.JdoDeleter#deleteByQuery(java.lang.Class,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public long deleteByQuery(Class<?> clazz, String queryString,
			Object parameters) throws Exception {

		// critical operation, so no delete without filters
		if (queryString == null || queryString.trim().isEmpty())
			return -1;

		PersistenceManager pm = null;
		try {
			pm = jdoService.getPM();
			Query query = pm.newQuery(clazz, queryString);
			return query.deletePersistentAll(parameters);
		} finally {
			jdoService.closePM(pm);
		}
	}
}
