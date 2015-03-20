# Jdo Util Wrapper
An simple wrapper for AppEngine datastore operations using JDO. Its not built in any intention to replace anything, writing some many line repeatedly for soame operations is really pain, So this library just wraps most frequently used jdo operations in simple one line method call. Use it if you find it useful.

## Features

- Simple method calls for frequently operations 
- Type inferred method
- Easy Query Builder
- Independent componets, so you can split up as u need 

## How to Download ?

- currently you can manually download the jar file here [JdoUtil-0.0.1.jar](https://github.com/ramesh-dev/JdoUtil/blob/master/src/dist/JdoUtil-0.0.1.jar)
- will update to maven central repo soon!


## Examples 

##### Persistence Manager

if you dont specify an default persistence manager factory will be created with name **"transactions-optional"** (assuming it is defined in **jdoconfig.xml** file)
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

##### Get Object by Id 

```Java
// by name (string)
User user = loader.get(User.class, "user@abc.com");

// by id (long)
User user = loader.get(User.class, 123);

// by key (Key object)
Key key = KeyFactory.createKey(parent, User.class, "user@abc.com");
User user = loader.get(User.class, key);
```
