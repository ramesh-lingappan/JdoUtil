package com.jdoutil;

import javax.jdo.JDOHelper;

import org.junit.Assert;
import org.junit.Test;

import com.JdoUtil.JdoUtil;
import com.JdoUtil.services.JdoService;
import com.JdoUtil.services.JdoServiceFactory;

public class JdoUtilTest extends JdoUtilBaseTestCase {

	@Test
	public void testDefaultServiceFactory() {
		JdoServiceFactory factory = new JdoServiceFactory();
		Assert.assertNotNull(factory);
	}

	@Test
	public void testCustomJdoFactory() {
		JdoServiceFactory factory = new JdoServiceFactory(
				JDOHelper.getPersistenceManagerFactory("transactions-optional"));
		Assert.assertNotNull(factory);
	}

	@Test
	public void testDefaultJdoService() {
		JdoService service = JdoUtil.service();

		Assert.assertNotNull(service);
		Assert.assertNotNull(service.factory());
		Assert.assertNotNull(service.factory().getPMF());
	}

	public void testJdoServiceWithCustomFactory(){

		JdoUtil.setFactory(new JdoServiceFactory());
		JdoService service = JdoUtil.service();

		Assert.assertNotNull(service);
		Assert.assertNotNull(service.factory());
		Assert.assertNotNull(service.factory().getPMF());
	}
}
