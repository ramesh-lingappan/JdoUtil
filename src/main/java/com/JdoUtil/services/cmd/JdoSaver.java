/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd;

import java.util.Collection;

/**
 * The Interface JdoSaver.
 */
public interface JdoSaver {

	/**
	 * Persist.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entity
	 *            the entity
	 * @return the t
	 * @throws Exception
	 *             the exception
	 */
	public abstract <T> T persist(T entity) throws Exception;

	/**
	 * Persist all.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entities
	 *            the entities
	 * @return the collection
	 * @throws Exception
	 *             the exception
	 */
	public abstract <T> Collection<T> persistAll(Collection<T> entities)
			throws Exception;

}