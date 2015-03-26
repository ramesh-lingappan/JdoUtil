/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd;

import java.util.Collection;
import java.util.List;

import com.JdoUtil.services.query.QueryBuilder;
import com.JdoUtil.services.query.QueryResult;

/**
 * The Interface JdoLoader.
 */
public interface JdoLoader {

	/**
	 * Gets the.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param key
	 *            the key
	 * @return the t
	 */
	public abstract <T> T get(Class<T> clazz, Object key);

	/**
	 * Gets the muti.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param keys
	 *            the keys
	 * @return the muti
	 */
	public abstract <T> List<T> getMuti(Class<T> clazz,
			Collection<? extends Object> keys);

	/**
	 * Fetch by query.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param queryString
	 *            the query string
	 * @return the t
	 */
	public abstract <T> T fetchByQuery(Class<T> clazz, String queryString);

	/**
	 * Fetch multi by query.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param queryString
	 *            the query string
	 * @return the list
	 */
	public abstract <T> List<T> fetchMultiByQuery(Class<T> clazz,
			String queryString);

	/**
	 * Fetch by contains query.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param propertyName
	 *            the property name
	 * @param list
	 *            the list
	 * @return the list
	 */
	public abstract <T> List<T> fetchByContainsQuery(Class<T> clazz,
			String propertyName, Collection<?> list);

	/**
	 * Fetch key by query.
	 *
	 * @param clazz
	 *            the clazz
	 * @param keyName
	 *            the key name
	 * @param queryString
	 *            the query string
	 * @return the object
	 */
	public abstract Object fetchKeyByQuery(Class<?> clazz, String keyName,
			String queryString);

	/**
	 * Fetch keys by query.
	 *
	 * @param clazz
	 *            the clazz
	 * @param keyName
	 *            the key name
	 * @param queryString
	 *            the query string
	 * @return the list
	 */
	public abstract List<Object> fetchKeysByQuery(Class<?> clazz,
			String keyName, String queryString);

	/**
	 * Fetch keys by query.
	 *
	 * @param <K>
	 *            the key type
	 * @param clazz
	 *            the clazz
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param queryString
	 *            the query string
	 * @return the list
	 */
	public abstract <K> List<K> fetchKeysByQuery(Class<?> clazz,
			String keyName, Class<K> keyType, String queryString);

	/**
	 * Execute query.
	 *
	 * @param <T>
	 *            the generic type
	 * @param builder
	 *            the builder
	 * @return the query result
	 */
	public abstract <T> QueryResult<T> executeQuery(QueryBuilder<T> builder);

}