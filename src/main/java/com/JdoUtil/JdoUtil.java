package com.JdoUtil;

import com.JdoUtil.services.JdoService;
import com.JdoUtil.services.JdoServiceFactory;
import com.JdoUtil.services.query.JdoQueryService;
import com.JdoUtil.services.query.QueryBuilder;

public class JdoUtil {

	private static JdoService _jdoService;
	private static JdoServiceFactory _serviceFactory;

	public static void setFactory(JdoServiceFactory factory) {
		_serviceFactory = factory;
	}

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

	public JdoServiceFactory factory() {
		return service().factory();
	}

	public static JdoQueryService loader() {
		return new JdoQueryService();
	}

	public static <T> QueryBuilder<T> queryBuilder(Class<T> clazz){
		return new QueryBuilder<T>(clazz);
	}
}
