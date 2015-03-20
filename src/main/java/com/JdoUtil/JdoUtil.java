/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil;

import com.JdoUtil.services.JdoService;
import com.JdoUtil.services.JdoServiceFactory;
import com.JdoUtil.services.query.JdoQueryService;
import com.JdoUtil.services.query.QueryBuilder;

/**
 * The Class JdoUtil.
 */
public class JdoUtil {

	/** The _jdo service. */
	private static JdoService _jdoService;

	/** The _service factory. */
	private static JdoServiceFactory _serviceFactory;

	/**
	 * Sets the factory.
	 *
	 * @param factory
	 *            the new factory
	 */
	public static void setFactory(JdoServiceFactory factory) {
		_serviceFactory = factory;
	}

	/**
	 * Service.
	 *
	 * @return the jdo service
	 */
	public static JdoService service() {
		if (_jdoService == null) {
			synchronized (JdoUtil.class) {
				if (_jdoService == null) {
					_jdoService = _serviceFactory == null ? new JdoService()
							: new JdoService(_serviceFactory);
					_serviceFactory = null;
				}
			}
		}

		return _jdoService;
	}

	/**
	 * Factory.
	 *
	 * @return the jdo service factory
	 */
	public JdoServiceFactory factory() {
		return service().factory();
	}

	/**
	 * Loader.
	 *
	 * @return the jdo query service
	 */
	public static JdoQueryService loader() {
		return new JdoQueryService();
	}

	/**
	 * Query builder.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the query builder
	 */
	public static <T> QueryBuilder<T> queryBuilder(Class<T> clazz) {
		return new QueryBuilder<T>(clazz);
	}
}
