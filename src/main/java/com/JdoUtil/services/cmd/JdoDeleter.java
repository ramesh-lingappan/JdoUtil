/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd;

import java.util.Collection;

/**
 * The Interface JdoDeleter.
 */
public interface JdoDeleter {

	/**
	 * Delete.
	 *
	 * @param entity the entity
	 * @return true, if successful
	 */
	boolean delete(Object entity);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#delete(java.lang.Class,
	 * java.lang.Object)
	 */
	/**
	 * Delete.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param key the key
	 * @return true, if successful
	 */
	public abstract <T> boolean delete(Class<T> clazz, Object key);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#deleteAll(java.util.Collection)
	 */
	/**
	 * Delete all.
	 *
	 * @param entities the entities
	 * @return true, if successful
	 */
	public abstract boolean deleteAll(Collection<?> entities);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#deleteByQuery(java.lang.Class,
	 * java.lang.String)
	 */
	/**
	 * Delete by query.
	 *
	 * @param clazz the clazz
	 * @param queryString the query string
	 * @return the long
	 * @throws Exception the exception
	 */
	public abstract long deleteByQuery(Class<?> clazz, String queryString)
			throws Exception;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#deleteByQuery(java.lang.Class,
	 * java.lang.String, java.lang.Object)
	 */
	/**
	 * Delete by query.
	 *
	 * @param clazz the clazz
	 * @param queryString the query string
	 * @param parameters the parameters
	 * @return the long
	 * @throws Exception the exception
	 */
	public abstract long deleteByQuery(Class<?> clazz, String queryString,
			Object parameters) throws Exception;

}