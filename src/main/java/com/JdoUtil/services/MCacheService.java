package com.JdoUtil.services;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class MCacheService {

	protected MemcacheService memCacheService;

	public MCacheService() {
		this(MemcacheServiceFactory.getMemcacheService());
	}

	public MCacheService(String namespace) {
		this(MemcacheServiceFactory.getMemcacheService(namespace));
	}

	public MCacheService(MemcacheService service) {
		this.memCacheService = service;

		if (this.memCacheService == null)
			throw new NullPointerException("invalid memcache service instance");
	}
}
