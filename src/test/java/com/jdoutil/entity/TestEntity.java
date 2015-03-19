package com.jdoutil.entity;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Query;

@PersistenceCapable
public class TestEntity {

	@PrimaryKey
	private String key;

	@Persistent
	private String testProperty1;
}
