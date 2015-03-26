/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd.impl;

import java.util.Collection;

import com.JdoUtil.services.JdoService;

/**
 * The Class JdoCacheDeleterImpl.
 */
public class JdoCacheDeleterImpl extends JdoDeleterImpl {

	/**
	 * Instantiates a new jdo cache deleter impl.
	 *
	 * @param jdoService the jdo service
	 */
	public JdoCacheDeleterImpl(JdoService jdoService) {
		super(jdoService);

	}

	/* (non-Javadoc)
	 * @see com.JdoUtil.services.deleter.JdoDeleterImpl#delete(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> boolean delete(Class<T> clazz, Object key) {
		// TODO Auto-generated method stub
		return super.delete(clazz, key);
	}

	/* (non-Javadoc)
	 * @see com.JdoUtil.services.deleter.JdoDeleterImpl#deleteAll(java.util.Collection)
	 */
	@Override
	public boolean deleteAll(Collection<?> entities) {
		// TODO Auto-generated method stub
		return super.deleteAll(entities);
	}



}
