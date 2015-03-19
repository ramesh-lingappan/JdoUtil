package com.JdoUtil.services.query;

import static com.JdoUtil.JdoUtil.queryBuilder;
import static com.JdoUtil.JdoUtil.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

public class JdoQueryService {

	public static PersistenceManager getPM() {
		return service().getPM();
	}

	public void closePM(PersistenceManager pm) {
		if (pm != null && !pm.isClosed())
			pm.close();
	}

	public <T> T get(Class<T> clazz, Object key) {

		PersistenceManager pm = null;

		try {
			pm = getPM();
			T entity = pm.getObjectById(clazz, key);

			if (entity != null)
				entity = pm.detachCopy(entity);

			return entity;
		} finally {
			closePM(pm);
		}
	}

	public <T> List<T> getMuti(Class<T> clazz, Collection<? extends Object> keys) {

		PersistenceManager pm = null;
		try {
			pm = getPM();
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
			closePM(pm);
		}
	}

	public <T> T persist(T entity) throws Exception {

		PersistenceManager pm = null;
		try {

			pm = getPM();

			T entityJDO = pm.makePersistent(entity);

			if (entityJDO != null)
				entityJDO = pm.detachCopy(entityJDO);

			return entityJDO;
		} finally {
			closePM(pm);
		}
	}

	public <T> Collection<T> persistAll(Collection<T> entities)
			throws Exception {

		PersistenceManager pm = null;
		try {

			pm = getPM();

			Collection<T> updatedEntities = pm.makePersistentAll(entities);

			if (!updatedEntities.isEmpty())
				updatedEntities = pm.detachCopyAll(updatedEntities);

			return updatedEntities;
		} finally {
			closePM(pm);
		}
	}

	public boolean delete(Object entity) {

		PersistenceManager pm = null;
		try {
			pm = getPM();
			pm.setCopyOnAttach(true);

			pm.deletePersistent(entity);
			return true;
		} finally {
			closePM(pm);
		}
	}

	public <T> boolean delete(Class<T> clazz, Object key) {

		PersistenceManager pm = null;
		try {
			pm = getPM();
			T entity = pm.getObjectById(clazz, key);
			if (entity == null)
				return false;

			pm.deletePersistent(entity);
			return true;
		} catch (JDOObjectNotFoundException e) {
			return false;
		} finally {
			closePM(pm);
		}
	}

	public boolean deleteAll(Collection<?> entities) {

		PersistenceManager pm = null;
		try {
			pm = getPM();
			pm.setCopyOnAttach(true);

			pm.deletePersistentAll(entities);
			return true;
		} finally {
			closePM(pm);
		}
	}

	public long deleteByQuery(Class<?> clazz, String queryString)
			throws Exception {

		// critical operation, so no delete without filters
		if (queryString == null || queryString.trim().isEmpty())
			return -1;

		PersistenceManager pm = null;
		try {
			pm = getPM();
			return pm.newQuery(clazz, queryString).deletePersistentAll();
		} finally {
			closePM(pm);
		}
	}

	public long deleteByQuery(Class<?> clazz, String queryString,
			Object parameters) throws Exception {

		// critical operation, so no delete without filters
		if (queryString == null || queryString.trim().isEmpty())
			return -1;

		PersistenceManager pm = null;
		try {
			pm = getPM();
			Query query = pm.newQuery(clazz, queryString);
			return query.deletePersistentAll(parameters);
		} finally {
			closePM(pm);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T fetchByQuery(Class<T> clazz, String queryString) {

		PersistenceManager pm = null;
		try {
			pm = getPM();
			Query query = pm.newQuery(clazz, queryString);

			List<T> entities = (List<T>) query.execute();
			if (entities.isEmpty())
				return null;

			return pm.detachCopy(entities.get(0));
		} finally {
			closePM(pm);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fetchMultiByQuery(Class<T> clazz, String queryString) {

		PersistenceManager pm = null;
		try {
			pm = getPM();
			Query query = pm.newQuery(clazz, queryString);

			List<T> entities = (List<T>) query.execute();
			if (!entities.isEmpty())
				entities = (List<T>) pm.detachCopyAll(entities);

			return entities;
		} finally {
			closePM(pm);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fetchByContainsQuery(Class<T> clazz,
			String propertyName, Collection<?> list) {

		PersistenceManager pm = null;
		try {
			pm = getPM();

			Query query = pm.newQuery(clazz, ":list.contains(" + propertyName
					+ ")");

			List<T> entities = (List<T>) query.execute(list);

			if (!entities.isEmpty())
				entities = (List<T>) pm.detachCopyAll(entities);

			return entities;
		} finally {
			closePM(pm);
		}
	}

	@SuppressWarnings("unchecked")
	public Object fetchKeyByQuery(Class<?> clazz, String keyName,
			String queryString) {

		PersistenceManager pm = null;
		try {
			pm = getPM();

			Query query = queryBuilder(clazz).keyOnly(keyName)
					.setFilter(queryString).getJdoQuery(pm);
			List<Object> result = (List<Object>) query.execute();

			return result.isEmpty() ? null : result.get(0);

		} finally {
			closePM(pm);
		}
	}

	public List<Object> fetchKeysByQuery(Class<?> clazz, String keyName,
			String queryString) {
		return fetchKeysByQuery(clazz, keyName, Object.class, queryString);
	}

	@SuppressWarnings("unchecked")
	public <K> List<K> fetchKeysByQuery(Class<?> clazz, String keyName,
			Class<K> keyType, String queryString) {
		PersistenceManager pm = null;

		try {
			pm = getPM();

			Query query = queryBuilder(clazz).keyOnly(keyName)
					.setFilter(queryString).getJdoQuery(pm);

			// Query query = pm.newQuery("SELECT " + keyName + " FROM "
			// + clazz.getName() + " WHERE " + queryString);

			// copying since it cannot be detached
			return new ArrayList<K>((List<K>) query.execute());
		} finally {
			closePM(pm);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> QueryResult<T> executeQuery(QueryBuilder<T> builder) {

		if (!QueryBuilder.isValid(builder))
			return null;

		PersistenceManager pm = null;
		try {
			pm = getPM();

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
			closePM(pm);
		}
	}

}
