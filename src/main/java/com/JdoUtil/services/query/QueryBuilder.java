/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.query;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

/**
 * The Class QueryBuilder.
 *
 * @param <T> the generic type
 */
public class QueryBuilder<T> {

	/** The clazz. */
	private final Class<T> clazz;
	
	/** The query. */
	private StringBuilder query = new StringBuilder();
	
	/** The query params. */
	private Object queryParams;
	
	/** The sort order. */
	private String cursorString, keyName, sortOrder;
	
	/** The need cursor. */
	private boolean needCursor;
	
	/** The end range. */
	private long startRange = 0, endRange = 0;

	/**
	 * Instantiates a new query builder.
	 *
	 * @param clazz the clazz
	 */
	public QueryBuilder(Class<T> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("invalid class type");
		this.clazz = clazz;
	}

	/**
	 * Clazz.
	 *
	 * @return the class
	 */
	public Class<T> clazz() {
		return clazz;
	}

	/**
	 * Cursor string.
	 *
	 * @param cursor the cursor
	 * @return the query builder
	 */
	public QueryBuilder<T> cursorString(String cursor) {
		this.cursorString = cursor;
		return this;
	}

	/**
	 * Need cursor.
	 *
	 * @return the query builder
	 */
	public QueryBuilder<T> needCursor() {
		this.needCursor = true;
		return this;
	}

	/**
	 * Key only.
	 *
	 * @param keyName the key name
	 * @return the query builder
	 */
	public QueryBuilder<T> keyOnly(String keyName) {

		this.keyName = keyName;
		return this;
	}

	/**
	 * Gets the query string.
	 *
	 * @return the query string
	 */
	public String getQueryString() {
		return query == null ? null : query.toString();
	}

	/**
	 * Checks for filters.
	 *
	 * @return true, if successful
	 */
	public boolean hasFilters() {
		return query == null ? false : !query.toString().trim().isEmpty();
	}

	/**
	 * Sets the filter.
	 *
	 * @param filter the filter
	 * @return the query builder
	 */
	public QueryBuilder<T> setFilter(String filter) {
		this.query.append(filter);
		return this;
	}

	/**
	 * And filter.
	 *
	 * @param filter the filter
	 * @return the query builder
	 */
	public QueryBuilder<T> andFilter(String filter) {
		setFilter("&&", filter);
		return this;
	}

	/**
	 * Or filter.
	 *
	 * @param filter the filter
	 * @return the query builder
	 */
	public QueryBuilder<T> orFilter(String filter) {
		setFilter("||", filter);
		return this;
	}

	/**
	 * Query params.
	 *
	 * @param params the params
	 * @return the query builder
	 */
	public QueryBuilder<T> queryParams(Object params) {
		this.queryParams = params;
		return this;
	}

	/**
	 * Gets the query params.
	 *
	 * @return the query params
	 */
	public Object getQueryParams() {
		return queryParams;
	}

	/**
	 * Sort asc.
	 *
	 * @param propertyName the property name
	 * @return the query builder
	 */
	public QueryBuilder<T> sortAsc(String propertyName) {
		if (propertyName != null)
			addToSortOrder(propertyName, "asc");
		return this;
	}

	/**
	 * Sort desc.
	 *
	 * @param propertyName the property name
	 * @return the query builder
	 */
	public QueryBuilder<T> sortDesc(String propertyName) {
		if (propertyName != null)
			addToSortOrder(propertyName, "desc");
		return this;
	}

	/**
	 * Adds the to sort order.
	 *
	 * @param propertyName the property name
	 * @param order the order
	 */
	private void addToSortOrder(String propertyName, String order) {
		if (this.sortOrder == null)
			sortOrder = "";
		else
			sortOrder += ",";
		sortOrder += addSpace(propertyName) + order.toLowerCase();
	}

	/**
	 * Gets the start range.
	 *
	 * @return the start range
	 */
	public long getStartRange() {
		return startRange;
	}

	/**
	 * Sets the start range.
	 *
	 * @param startRange the start range
	 * @return the query builder
	 */
	public QueryBuilder<T> setStartRange(long startRange) {
		this.startRange = startRange;
		return this;
	}

	/**
	 * Gets the end range.
	 *
	 * @return the end range
	 */
	public long getEndRange() {
		return endRange;
	}

	/**
	 * Sets the end range.
	 *
	 * @param endRange the end range
	 * @return the query builder
	 */
	public QueryBuilder<T> setEndRange(long endRange) {
		this.endRange = endRange;
		return this;
	}

	/**
	 * Gets the where filters.
	 *
	 * @return the where filters
	 */
	public String getWhereFilters() {
		if (!hasFilters())
			return null;
		String builder = query.toString();
		if (hasValidRange())
			builder += " RANGE " + startRange + "," + endRange;

		if (hasSortOrder())
			builder += " ORDER BY " + sortOrder;
		return clearSpace(builder);
	}

	/**
	 * Gets the full query.
	 *
	 * @return the full query
	 */
	public String getFullQuery() {

		StringBuilder q = new StringBuilder("SELECT");
		if (isKeyOnly())
			q.append(addSpace(keyName));
		q.append(" FROM " + clazz.getName());

		String filters = getWhereFilters();
		if (filters != null)
			q.append(" WHERE " + filters);
		return clearSpace(q.toString());
	}

	/**
	 * Gets the jdo query.
	 *
	 * @param pm the pm
	 * @return the jdo query
	 */
	public Query getJdoQuery(PersistenceManager pm) {
		if (pm == null)
			throw new IllegalArgumentException("persistence manager is null");

		Query jdoQuery = pm.newQuery(getFullQuery());

		// hasFilters() ? pm.newQuery(clazz, query.toString())
		// : pm.newQuery(clazz);
		//
		// if (hasValidRange())
		// jdoQuery.setRange(startRange, endRange);
		//
		// if (hasSortOrder())
		// jdoQuery.setOrdering(sortOrder);

		Map<String, Object> cursorMap = getCursorMap();
		if (cursorMap != null)
			jdoQuery.setExtensions(cursorMap);
		return jdoQuery;
	}

	/**
	 * Checks for valid range.
	 *
	 * @return true, if successful
	 */
	public boolean hasValidRange() {
		return (this.startRange >= 0 && this.endRange > startRange);
	}

	/**
	 * Checks for cursor.
	 *
	 * @return true, if successful
	 */
	public boolean hasCursor() {
		return !(this.cursorString == null || this.cursorString.trim()
				.isEmpty());
	}

	/**
	 * Need cursor string.
	 *
	 * @return true, if successful
	 */
	public boolean needCursorString() {
		return needCursor;
	}

	/**
	 * Gets the cursor map.
	 *
	 * @return the cursor map
	 */
	public Map<String, Object> getCursorMap() {
		if (!hasCursor())
			return null;
		Cursor cursor = Cursor.fromWebSafeString(cursorString);
		Map<String, Object> extensionMap = new HashMap<String, Object>();
		extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);

		return extensionMap;
	}

	/**
	 * Checks if is key only.
	 *
	 * @return true, if is key only
	 */
	public boolean isKeyOnly() {
		return !(keyName == null || keyName.trim().isEmpty());
	}

	/**
	 * Checks for sort order.
	 *
	 * @return true, if successful
	 */
	public boolean hasSortOrder() {
		return !(sortOrder == null || sortOrder.trim().isEmpty());
	}

	/**
	 * Adds the space.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	private String addSpace(String obj) {
		return obj == null ? "" : " " + obj + " ";
	}

	/**
	 * Clear space.
	 *
	 * @param val the val
	 * @return the string
	 */
	private String clearSpace(String val) {
		return val == null ? null : val.replaceAll("\\s+", " ").trim();
	}

	/**
	 * Sets the filter.
	 *
	 * @param filterType the filter type
	 * @param filter the filter
	 */
	private void setFilter(String filterType, String filter) {
		if (!hasFilters())
			throw new IllegalArgumentException(
					"no inital filter is set, cannot add condition first");
		if (filter != null)
			this.query.append(addSpace(filterType) + filter.trim());
	}

	/**
	 * Checks if is valid.
	 *
	 * @param builder the builder
	 * @return true, if is valid
	 */
	public static boolean isValid(QueryBuilder<?> builder) {
		return !(builder == null || builder.getClass() == null);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getFullQuery();
	}
}
