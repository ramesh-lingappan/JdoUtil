/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil;

import com.JdoUtil.services.JdoService;
import com.JdoUtil.services.JdoServiceFactory;
import com.JdoUtil.services.cmd.JdoDeleter;
import com.JdoUtil.services.cmd.JdoLoader;
import com.JdoUtil.services.cmd.JdoSaver;
import com.JdoUtil.services.query.QueryBuilder;

/**
 * The Class JdoUtil.
 */
public class JdoUtil {

	/** The _jdo service. */
	private static JdoService _jdoService;

	/** The _service factory. */
	private static JdoServiceFactory _serviceFactory = new JdoServiceFactory();

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
					_jdoService = new JdoService(_serviceFactory);
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
	public static JdoLoader loader() {
		return service().loader();
	}

	/**
	 * Loader.
	 *
	 * @param cache
	 *            the cache
	 * @return the jdo loader
	 */
	public static JdoLoader loader(boolean cache) {
		return service().loader(cache);
	}

	/**
	 * Saver.
	 *
	 * @return the jdo saver
	 */
	public static JdoSaver saver() {
		return service().saver();
	}

	/**
	 * Saver.
	 *
	 * @param cache the cache
	 * @return the jdo saver
	 */
	public static JdoSaver saver(boolean cache) {
		return service().saver(cache);
	}

	/**
	 * Deleter.
	 *
	 * @return the jdo deleter
	 */
	public static JdoDeleter deleter() {
		return service().deleter();
	}

	/**
	 * Deleter.
	 *
	 * @param cache the cache
	 * @return the jdo deleter
	 */
	public static JdoDeleter deleter(boolean cache) {
		return service().deleter(cache);
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
