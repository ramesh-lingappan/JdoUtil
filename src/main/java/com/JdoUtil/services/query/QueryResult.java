/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.query;

import java.util.List;

/**
 * The Class QueryResult.
 *
 * @param <T> the generic type
 */
public class QueryResult<T> {

	/** The success. */
	private boolean success;

	// detached copy
	/** The result. */
	private List<T> result;

	/** The keys. */
	private List<? extends Object> keys;

	/** The cursor str. */
	private String cursorStr;

	/**
	 * Instantiates a new query result.
	 */
	public QueryResult() {
	}

	/**
	 * Checks if is success.
	 *
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Sets the success.
	 *
	 * @param success the new success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Instantiates a new query result.
	 *
	 * @param result the result
	 */
	public QueryResult(List<T> result) {
		this.result = result;
	}

	/**
	 * Instantiates a new query result.
	 *
	 * @param result the result
	 * @param cursorStr the cursor str
	 */
	public QueryResult(List<T> result, String cursorStr) {
		this.result = result;
		this.cursorStr = cursorStr;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * Gets the result size.
	 *
	 * @return the result size
	 */
	public int getResultSize() {
		return result == null ? 0 : result.size();
	}

	/**
	 * Gets the first.
	 *
	 * @return the first
	 */
	public T getFirst() {
		if (this.result == null || this.result.isEmpty())
			return null;
		return result.get(0);
	}

	/**
	 * Sets the result.
	 *
	 * @param result the result
	 * @return the query result
	 */
	public QueryResult<T> setResult(List<T> result) {
		this.result = result;
		return this;
	}

	/**
	 * Sets the keys.
	 *
	 * @param keys the keys
	 * @return the query result
	 */
	public QueryResult<T> setKeys(List<? extends Object> keys) {
		this.keys = keys;
		return this;
	}

	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getKeys() {
		return (List<Object>) this.keys;
	}

	/**
	 * Gets the keys.
	 *
	 * @param <E> the element type
	 * @param clazz the clazz
	 * @return the keys
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> getKeys(Class<E> clazz){
		if(this.keys == null)
			return null;
		return (List<E>)this.keys;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public Object getKey() {
		if (this.keys == null || this.keys.isEmpty())
			return null;
		return this.keys.get(0);
	}

	/**
	 * Gets the key.
	 *
	 * @param <E> the element type
	 * @param clazz the clazz
	 * @return the key
	 */
	@SuppressWarnings("unchecked")
	public <E> E getKey(Class<E> clazz) {
		Object key = getKey();
		return key == null ? null : (E) key;
	}

	/**
	 * Gets the cursor str.
	 *
	 * @return the cursor str
	 */
	public String getCursorStr() {
		return cursorStr;
	}

	/**
	 * Sets the cursor str.
	 *
	 * @param cursorStr the cursor str
	 * @return the query result
	 */
	public QueryResult<T> setCursorStr(String cursorStr) {
		this.cursorStr = cursorStr;
		return this;
	}

}
