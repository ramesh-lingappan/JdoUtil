/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd.impl;

import java.util.Collection;
import java.util.List;

import com.JdoUtil.services.JdoService;

/**
 * The Class JdoCacheLoaderImpl.
 */
public class JdoCacheLoaderImpl extends JdoLoaderImpl {

	/**
	 * Instantiates a new jdo cache loader impl.
	 *
	 * @param service the service
	 */
	public JdoCacheLoaderImpl(JdoService service) {
		super(service);
	}

	/* (non-Javadoc)
	 * @see com.JdoUtil.services.loader.JdoLoaderImpl#get(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T get(Class<T> clazz, Object key) {

		return super.get(clazz, key);
	}

	/* (non-Javadoc)
	 * @see com.JdoUtil.services.loader.JdoLoaderImpl#getMuti(java.lang.Class, java.util.Collection)
	 */
	@Override
	public <T> List<T> getMuti(Class<T> clazz, Collection<? extends Object> keys) {
		// TODO Auto-generated method stub
		return super.getMuti(clazz, keys);
	}

}
