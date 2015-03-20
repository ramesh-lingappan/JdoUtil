# Jdo Utility Wrapper
An simple wrapper for AppEngine datastore operations using JDO. Its not built in any intention to replace anything, writing some many line repeatedly for soame operations is really pain, So this library just wraps most frequently used jdo operations in simple one line method call. Use it if you find it useful.

## Features

- Simple method calls for frequently operations 
- Type inferred method
- Easy Query Builder
- Independent componets, so you can split up as u need 
- Libray is 15KB sized

## How to Download ?

- currently you can manually download the jar file here [JdoUtil-0.0.1.jar](https://github.com/ramesh-dev/JdoUtil/blob/master/src/dist/JdoUtil-0.0.1.jar)
- will update to maven central repo soon!


## Examples 

##### Persistence Manager

if you dont specify an PersistenceManagerFactory, a default PersistenceManagerFactory  will be created with name **"transactions-optional"** (assuming it is defined in **jdoconfig.xml** file) like below
```xml
<persistence-manager-factory name="transactions-optional">
...
 </persistence-manager-factory>
 ```


```Java
// you dont need to create this if you are default like above
JdoServiceFactory factory = new JdoServiceFactory();
```

##### Jdo Service with default Persistence Manager

Jdo service is an singleton which hold the Persistence Manager and act as an Wrapper like PMF file

```Java
JdoService service = JdoUtil.service();

// get PMF 
PersistenceManagerFactory pmf = service.getPMF();

// get Persistence Manager Instance
PersistenceManager pm = service.getPM();
```

##### Jdo Service with Custom Persistence Manager 

you can set your own persistence manager to be used by Jdo Service like below

```Java
JdoServiceFactory factory = new JdoServiceFactory("some-other-name");
JdoUtil.setFactory(factory);

// now jdo service instance will use custom persistence manager
JdoService service = JdoUtil.service();
```

## JDO Operations 

##### Jdo Query Service 
Use query serivce either using JdoUtil or by new instance;
```Java
JdoQueryService loader = JdoUtil.loader();
loader.get(User.class,"user@abc.com");
```

##### Get Object by Id 

```Java
// by name (string)
// dont worry all returned as detached
User user = JdoUtil.loader().get(User.class, "user@abc.com");

// by id (long)
User user = JdoUtil.loader().get(User.class, 123);

// by key (Key object)
Key key = KeyFactory.createKey(parent, User.class, "user@abc.com");
User user = JdoUtil.loader().get(User.class, key);
```

##### Get Multiple Objects By Ids 

```Java
Set<String> keys = new HashSet<String>();
keys.add("user@abc.com");
keys.add("user2@abc.com");

List<User> users = JdoUtil.loader().getMuti(User.class, keys);
```

##### Save an Entity 

```Java
// returns detached version of entity
User user = JdoUtil.loader().persist(user);
```

##### Save Multiple Entities 
```Java

	Collection<User> users; // list of entities to be save
	List<User> user = (List<User>) JdoUtil.loader().persistAll(users);
```

### Delete an Entity
```Java
boolean deleted = JdoUtil.loader().delete(user);
```

### Delete by Entity Key
```Java
boolean deleted = JdoUtil.loader().delete(User.class,"user@abc.com");
```

yet to add more examples, but you can place around with the jar!
