package com.JdoUtil.services.query;

import java.util.List;

public class QueryResult<T> {

	private boolean success;

	// detached copy
	private List<T> result;

	private List<? extends Object> keys;

	private String cursorStr;

	public QueryResult() {
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public QueryResult(List<T> result) {
		this.result = result;
	}

	public QueryResult(List<T> result, String cursorStr) {
		this.result = result;
		this.cursorStr = cursorStr;
	}

	public List<T> getResult() {
		return result;
	}

	public int getResultSize() {
		return result == null ? 0 : result.size();
	}

	public T getFirst() {
		if (this.result == null || this.result.isEmpty())
			return null;
		return result.get(0);
	}

	public QueryResult<T> setResult(List<T> result) {
		this.result = result;
		return this;
	}

	public QueryResult<T> setKeys(List<? extends Object> keys) {
		this.keys = keys;
		return this;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getKeys() {
		return (List<Object>) this.keys;
	}

	@SuppressWarnings("unchecked")
	public <E> List<E> getKeys(Class<E> clazz){
		if(this.keys == null)
			return null;
		return (List<E>)this.keys;
	}

	public Object getKey() {
		if (this.keys == null || this.keys.isEmpty())
			return null;
		return this.keys.get(0);
	}

	@SuppressWarnings("unchecked")
	public <E> E getKey(Class<E> clazz) {
		Object key = getKey();
		return key == null ? null : (E) key;
	}

	public String getCursorStr() {
		return cursorStr;
	}

	public QueryResult<T> setCursorStr(String cursorStr) {
		this.cursorStr = cursorStr;
		return this;
	}

}
