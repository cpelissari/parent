# objectos comuns :: testing :: DBUnit

Easiear database testing with DBUnit, Maven and Guice.

Heavily inspired by the (now old) JBoss Seam 2.1.2 DBUnitSeamTest.

## Usage

Usage is quite simple.

Write a XML (sorry, couldn't escape that...) with your test data

```
<?xml version="1.0" encoding="UTF-8"?>
<dataset>
	<MY_DB.USER ID="1" USERNAME="joe" /> 
	<MY_DB.USER ID="2" USERNAME="moe" />
	<MY_DB.USER ID="3" USERNAME="anon" />
</dataset>
```

save it to src/test/resources/dbunit/mini-database.xml

Create a class to reference the mini file:

```java
public class MiniDatabaseXml extends DataSupplier {
  @Override
  public String getFilename() {
  	// name MUST be the one defined earlier
    return "mini-database.xml";
  }
}
```

Configure a Guice module

```java
public class MyModule extends AbstractModule {
  @Override
  protected void configure() {
    Provider<JdbcCredentials> credentials = new MyCredentialsProvider();

    install(new DbunitModuleBuilder() //
        .jdbc(credentials) //
        .withMysql() // overrides the default Hsqldb
        .build());
  }
}

public class MyCredentialsProvider implements Provider<JdbcCredentials> {
  @Override
  public JdbcCredentials get() {
  	return new JdbcCredentialsBuilder() //
  		.driverClass("com.mysql.jdbc.Driver") //
  		.url("jdbc:mysql://localhost/mydb") //
  		.user("test") //
  		.password("mypass") //
  		.get();
  }
}
```

Test it! (With TestNG, for instance)

```java
@Test
@Guice(modules = {MyModule.class}
public class MyTest {

  @Inject
  private DBUnit dbunit;
  
  @BeforeClass
  public void loadData() {
    dbunit.load(new MyDatabaseXml());
  }
  
  // actual tests...

}
```

## More info

Full docs will be available soon (hopefully).

Still no Maven Repo, sorry. You have to build it yourself.

You can find the author at Twitter @marcioendo.

## License

Copyright 2011 objectos, fábrica de software LTDA

Distributed under the Apache License, Version 2.0

http://www.apache.org/licenses/LICENSE-2.0 
