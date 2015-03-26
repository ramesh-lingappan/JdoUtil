/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd.impl;

import static com.JdoUtil.JdoUtil.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.JdoUtil.services.JdoService;
import com.JdoUtil.services.cmd.JdoLoader;
import com.JdoUtil.services.query.QueryBuilder;
import com.JdoUtil.services.query.QueryResult;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

/**
 * The Class JdoQueryService.
 */
public class JdoLoaderImpl implements JdoLoader {

	/** The service. */
	protected JdoService service;

	/**
	 * Instantiates a new jdo loader impl.
	 *
	 * @param service the service
	 */
	public JdoLoaderImpl(JdoService service) {
		this.service = service;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#get(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T> T get(Class<T> clazz, Object key) {

		PersistenceManager pm = null;

		try {
			pm = service.getPM();
			T entity = pm.getObjectById(clazz, key);

			if (entity != null)
				entity = pm.detachCopy(entity);

			return entity;
		} finally {
			service.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#getMuti(java.lang.Class,
	 * java.util.Collection)
	 */
	@Override
	public <T> List<T> getMuti(Class<T> clazz, Collection<? extends Object> keys) {

		PersistenceManager pm = null;
		try {
			pm = service.getPM();
			List<T> entities = new ArrayList<>();

			for (Object key : keys) {
				try {
					T entity = pm.getObjectById(clazz, key);
					if (entity != null)
						entities.add(entity);

				} catch (JDOObjectNotFoundException e) {
				}
			}

			if (!entities.isEmpty())
				entities = (List<T>) pm.detachCopyAll(entities);

			return entities;
		} finally {
			service.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.JdoUtil.services.query.JdoLoader#fetchByQuery(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T fetchByQuery(Class<T> clazz, String queryString) {

		PersistenceManager pm = null;
		try {
			pm = service.getPM();
			Query query = pm.newQuery(clazz, queryString);

			List<T> entities = (List<T>) query.execute();
			if (entities.isEmpty())
				return null;

			return pm.detachCopy(entities.get(0));
		} finally {
			service.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.query.JdoLoader#fetchMultiByQuery(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> fetchMultiByQuery(Class<T> clazz, String queryString) {

		PersistenceManager pm = null;
		try {
			pm = service.getPM();
			Query query = pm.newQuery(clazz, queryString);

			List<T> entities = (List<T>) query.execute();
			if (!entities.isEmpty())
				entities = (List<T>) pm.detachCopyAll(entities);

			return entities;
		} finally {
			service.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.query.JdoLoader#fetchByContainsQuery(java.lang.Class
	 * , java.lang.String, java.util.Collection)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> fetchByContainsQuery(Class<T> clazz,
			String propertyName, Collection<?> list) {

		PersistenceManager pm = null;
		try {
			pm = service.getPM();

			Query query = pm.newQuery(clazz, ":list.contains(" + propertyName
					+ ")");

			List<T> entities = (List<T>) query.execute(list);

			if (!entities.isEmpty())
				entities = (List<T>) pm.detachCopyAll(entities);

			return entities;
		} finally {
			service.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.query.JdoLoader#fetchKeyByQuery(java.lang.Class,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object fetchKeyByQuery(Class<?> clazz, String keyName,
			String queryString) {

		PersistenceManager pm = null;
		try {
			pm = service.getPM();

			Query query = queryBuilder(clazz).keyOnly(keyName)
					.setFilter(queryString).getJdoQuery(pm);
			List<Object> result = (List<Object>) query.execute();

			return result.isEmpty() ? null : result.get(0);

		} finally {
			service.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.query.JdoLoader#fetchKeysByQuery(java.lang.Class,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<Object> fetchKeysByQuery(Class<?> clazz, String keyName,
			String queryString) {
		return fetchKeysByQuery(clazz, keyName, Object.class, queryString);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.query.JdoLoader#fetchKeysByQuery(java.lang.Class,
	 * java.lang.String, java.lang.Class, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <K> List<K> fetchKeysByQuery(Class<?> clazz, String keyName,
			Class<K> keyType, String queryString) {
		PersistenceManager pm = null;

		try {
			pm = service.getPM();

			Query query = queryBuilder(clazz).keyOnly(keyName)
					.setFilter(queryString).getJdoQuery(pm);

			// copying since it cannot be detached
			return new ArrayList<K>((List<K>) query.execute());
		} finally {
			service.closePM(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.JdoUtil.services.query.JdoLoader#executeQuery(com.JdoUtil.services
	 * .query.QueryBuilder)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> QueryResult<T> executeQuery(QueryBuilder<T> builder) {

		if (!QueryBuilder.isValid(builder))
			return null;

		PersistenceManager pm = null;
		try {
			pm = service.getPM();

			Query jdoQuery = builder.getJdoQuery(pm);
			List<Object> result = (List<Object>) jdoQuery.execute();

			QueryResult<T> queryResult = new QueryResult<T>();

			if (!result.isEmpty()) {
				if (builder.needCursorString()
						&& result.size() >= (builder.getEndRange() - builder
								.getStartRange())) {
					Cursor cursor = JDOCursorHelper.getCursor(result);
					if (cursor != null)
						queryResult.setCursorStr(cursor.toWebSafeString());
				}
				if (builder.isKeyOnly()) {
					queryResult.setKeys(new ArrayList<Object>(result));
				} else {
					queryResult.setResult((List<T>) pm
							.detachCopyAll((List<T>) result));
				}
			}
			queryResult.setSuccess(true);
			return queryResult;
		} finally {
			service.closePM(pm);
		}
	}

}
