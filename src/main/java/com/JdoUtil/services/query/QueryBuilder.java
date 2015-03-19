package com.JdoUtil.services.query;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

public class QueryBuilder<T> {

	private final Class<T> clazz;
	private StringBuilder query = new StringBuilder();
	private Object queryParams;
	private String cursorString, keyName, sortOrder;
	private boolean needCursor;
	private long startRange = 0, endRange = 0;

	public QueryBuilder(Class<T> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("invalid class type");
		this.clazz = clazz;
	}

	public Class<T> clazz() {
		return clazz;
	}

	public QueryBuilder<T> cursorString(String cursor) {
		this.cursorString = cursor;
		return this;
	}

	public QueryBuilder<T> needCursor() {
		this.needCursor = true;
		return this;
	}

	public QueryBuilder<T> keyOnly(String keyName) {

		this.keyName = keyName;
		return this;
	}

	public String getQueryString() {
		return query == null ? null : query.toString();
	}

	public boolean hasFilters() {
		return query == null ? false : !query.toString().trim().isEmpty();
	}

	public QueryBuilder<T> setFilter(String filter) {
		this.query.append(filter);
		return this;
	}

	public QueryBuilder<T> andFilter(String filter) {
		setFilter("&&", filter);
		return this;
	}

	public QueryBuilder<T> orFilter(String filter) {
		setFilter("||", filter);
		return this;
	}

	public QueryBuilder<T> queryParams(Object params) {
		this.queryParams = params;
		return this;
	}

	public Object getQueryParams() {
		return queryParams;
	}

	public QueryBuilder<T> sortAsc(String propertyName) {
		if (propertyName != null)
			addToSortOrder(propertyName, "asc");
		return this;
	}

	public QueryBuilder<T> sortDesc(String propertyName) {
		if (propertyName != null)
			addToSortOrder(propertyName, "desc");
		return this;
	}

	private void addToSortOrder(String propertyName, String order) {
		if (this.sortOrder == null)
			sortOrder = "";
		else
			sortOrder += ",";
		sortOrder += addSpace(propertyName) + order.toLowerCase();
	}

	public long getStartRange() {
		return startRange;
	}

	public QueryBuilder<T> setStartRange(long startRange) {
		this.startRange = startRange;
		return this;
	}

	public long getEndRange() {
		return endRange;
	}

	public QueryBuilder<T> setEndRange(long endRange) {
		this.endRange = endRange;
		return this;
	}

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

	public boolean hasValidRange() {
		return (this.startRange >= 0 && this.endRange > startRange);
	}

	public boolean hasCursor() {
		return !(this.cursorString == null || this.cursorString.trim()
				.isEmpty());
	}

	public boolean needCursorString() {
		return needCursor;
	}

	public Map<String, Object> getCursorMap() {
		if (!hasCursor())
			return null;
		Cursor cursor = Cursor.fromWebSafeString(cursorString);
		Map<String, Object> extensionMap = new HashMap<String, Object>();
		extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);

		return extensionMap;
	}

	public boolean isKeyOnly() {
		return !(keyName == null || keyName.trim().isEmpty());
	}

	public boolean hasSortOrder() {
		return !(sortOrder == null || sortOrder.trim().isEmpty());
	}

	private String addSpace(String obj) {
		return obj == null ? "" : " " + obj + " ";
	}

	private String clearSpace(String val) {
		return val == null ? null : val.replaceAll("\\s+", " ").trim();
	}

	private void setFilter(String filterType, String filter) {
		if (!hasFilters())
			throw new IllegalArgumentException(
					"no inital filter is set, cannot add condition first");
		if (filter != null)
			this.query.append(addSpace(filterType) + filter.trim());
	}

	public static boolean isValid(QueryBuilder<?> builder) {
		return !(builder == null || builder.getClass() == null);
	}

	@Override
	public String toString() {
		return getFullQuery();
	}
}
