# Jdo Utility Wrapper
An simple wrapper for AppEngine datastore operations using JDO. Its not built in any intention to replace anything, writing some many line repeatedly for same operations is really pain, So this library just wraps most frequently used jdo operations in simple one line method call. Use it if you find it useful.

##### Note 
if you are starting new project, please use [Objectify](https://code.google.com/p/objectify-appengine/). Its really awesome and has lot of features, JdoUtil is build for existing appengine jdo users.

## Features

- Simple method calls for frequently operations 
- Type inferred method
- Easy Query Builder
- Independent componets, so you can split up as u need 
- Libray is 15KB sized

## How to Download ?

- currently you can manually download the jar file here [Download Jar File](https://github.com/ramesh-dev/JdoUtil/tree/master/release)
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
// you dont need to create this if you are using defaults like above
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

##### CRUD services 
you can use individual serivce either using JdoUtil or JdoService;
```Java
JdoLoader loader = JdoUtil.loader();

// enable or disable cache
JdoSaver saver = JdoUtil.saver(true);

JdoDeleter deleter = JdoUtil.deleter();
```

## Save Operations

##### Save an Entity 

```Java
// returns detached version of entity
User user = JdoUtil.saver().persist(user);
```

##### Save Multiple Entities 

```Java
Collection<User> users; // list of entities to be save
List<User> user = (List<User>) JdoUtil.saver().persistAll(users);
```
## Delete Operations

##### Delete an Entity
```Java
boolean deleted = JdoUtil.deleter().delete(user);
```

##### Delete Entity by Key
```Java
boolean deleted = JdoUtil.deleter().delete(User.class,"user@abc.com");
```

##### Delete Entities by Keys
```Java
boolean deleted = JdoUtil.deleter().deleteAll(users);
```

##### Delete Entities by Query
```Java
long deletedCount = JdoUtil.deleter().deleteByQuery(User.class,"shouldDelete == true");
```
### Loader Operations

#### Get Operations

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

#### Fetch Operations 

##### Fetch Single Entity By Query 
```Java
User user = JdoUtil.loader().fetchByQuery(User.class, "email == 'user@abc.com' && status == 'active' ");
```

##### Fetch Entities by Query
```Java
List<User> users = JdoUtil.loader().fetchMultiByQuery(User.class, "status == 'active'");
```

##### Fetch Entities by Contains Query
return list of entitties whos property value is contained in the specified list
```Java
List<User> users = JdoUtil.loader().fetchByContainsQuery(User.class, "accountKey", acctKeys);
```

##### Fetch Entity Key only by Query
fetchs only entity key
```Java
String key = (String) JdoUtil.loader().fetchKeyByQuery(User.class, "key", "email == 'user@abc.com'");
```

##### Fetch Entities Keys by Query
fetchs all entities keys which matchs the query 
```Java
List<String> keys = JdoUtil.loader().fetchKeysByQuery(User.class, "key", String.class, "status == 'active'");
```
### Query Builder 
Finally if you want more control over querying, you can use the QueryBuilder class to build and execute query like below, 

```Java
QueryBuilder<User> builder = JdoUtil.queryBuilder(User.class)
				.setFilter("email == 'user@abc.com'")
				.andFilter("status == 'active' ")
				.orFilter("status == 'pending' ")
				.keyOnly("key")
				.cursorString("cursor returned during previous fetch")
				.needCursor()
				.setEndRange(20)
				.sortDesc("dateAdded");
				
// get full query string 
String query = builder.getFullQuery();
		
// get where clause 
String whereClause = builder.getWhereFilters();
		
// or execute directly 
QueryResult<User> result = JdoUtil.loader().executeQuery(builder);
		
// get returned entities 
result.getResult();

// if key only query, can get keys like below
result.getKeys();
		
// returns cursor string , if needCursor() is specified in query builder
result.getCursorStr();
```
