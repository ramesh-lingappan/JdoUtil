/*
 * @author Ramesh Lingappa
 */
package com.JdoUtil.services.cmd.impl;

import java.util.Collection;

import com.JdoUtil.services.JdoService;

/**
 * The Class JdoCacheSaverImpl.
 */
public class JdoCacheSaverImpl extends JdoSaverImpl {

	/**
	 * Instantiates a new jdo cache saver impl.
	 *
	 * @param jdoService the jdo service
	 */
	public JdoCacheSaverImpl(JdoService jdoService) {
		super(jdoService);
	}

	/* (non-Javadoc)
	 * @see com.JdoUtil.services.saver.JdoSaverImpl#persist(java.lang.Object)
	 */
	@Override
	public <T> T persist(T entity) throws Exception {
		// TODO Auto-generated method stub
		return super.persist(entity);
	}

	/* (non-Javadoc)
	 * @see com.JdoUtil.services.saver.JdoSaverImpl#persistAll(java.util.Collection)
	 */
	@Override
	public <T> Collection<T> persistAll(Collection<T> entities)
			throws Exception {
		// TODO Auto-generated method stub
		return super.persistAll(entities);
	}

}
