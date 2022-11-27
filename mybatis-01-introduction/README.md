# 开发我的第一个MyBatis程序

1. resources目录：

    ```
    放在这个目录当中的，一般都是资源文件，配置文件。
    直接放到resources目录下的资源，等同于放到了类的根路径下。
   ```
   
2. 开发步骤：

```
    第一步：打包方式：jar
    第二步：引入依赖
            - mybatis依赖
            - mysql驱动依赖
    第三步：编写MyBatis核心配置文件：mybatis-config.xml
         注意：
            第一：这个文件名不是必须叫做mybatis-config.xml，可以用其它的名字。
            第二：这个文件放的位置也不是固定的，可以随意，但一般情况下，会放到类的根路径下。
         
         mybatis-config.xml中先把连接数据库的信息修改一下即可。
    第四步：编写XxxxMapper.xml文件
         在这个配置文件中编写SQL语句。
         这个文件名也不是固定的，放的位置也不是固定的。
    第五步：在mybatis-config.xml文件中指定XxxxMapper.xml文件的路径：
         <mapper resource="CarMapper.xml"/>
         注意：resource属性会自动从类的根路径下开始查找资源。
    第六步：编写Mybatis程序。（使用Mybatis的类库，编写Mybatis程序，连接数据库，做增删改查。）
         在Mybatis当中，负责执行SQL语句的那个对象叫做什么呢？
            SqlSession
         SqlSession是专门用来执行SQL语句的，是一个Java程序和数据库之间的一次会话。
         要想获取SqlSession对象，需要先获取SqlSessionFactory对象，通过SqlSessionFactory工厂来生产SqlSession对象。
         怎么获取SqlSessionFactory对象呢？
            需要首先获取SqlSessionFactoryBuilder对象。
            通过SqlSessionFactoryBuilder对象的build方法，来获取一个SqlSessionFactory对象。
            
         Mybatis的核心对象包括：
            SqlSessionFactoryBuilder
            SqlSessionFactory
            SqlSession
         
         SqlSessionFactoryBuilder --> SqlSessionFactory --> SqlSession
```

3. 从XML中构建SqlSessionFactory

```
   第一：在MyBatis中一定是有一个很重要的对象，这个对象是：SqlSessionFactory。
   第二：SqlSessionFactory对象的创建需要XML。
```

4. mybatis中有两个主要的配置文件：

```
   其中一个是：mybatis-config.xml，这是核心配置文件，主要配置连接数据库的信息等。（一个）
   另一个是：XxxxMapper.xml，这个文件是专门用来编写SQL语句的配置文件。（一个表一个）
```

5. 关于第一个程序的细节：

```
   * Mybatis中sql语句的结尾";"可以省略。
   * Resources.getResourceAsStream
      小技巧：以后凡是遇到resource这个单词，大部分情况下，这种加载资源的方式就是从类的根路径下开始加载。（开始查找）
      优点：采用这种方式，从类路径中加载资源，项目的移植性很强。
   * InputStream is = new FileInputStream();
      采用这种方式也可以。
      缺点：可移植性太差，程序不够健壮。可能会移植到其它的操作系统中。导致以上路径无效，还需要修改Java代码中的路径，这样违背了OCP原则。
   * InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
      ClassLoader.getSystemClassLoader()获取系统的类加载器。
      系统类加载器有一个方法：getResourceAsStream()
      它就是从类路径当中加载资源的。
      通过源代码分析：
         InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
         底层的源代码其实就是：
         InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
   * CarMapper.xml文件的名字是固定的吗？CarMapper.xml文件的路径是固定的吗？
      都不是固定的。
      <mapper resource="CarMapper.xml"/>
      <mapper url="file:///Users/turbo/Desktop/Projects/JavaProjects/MyBatis_Stu/mybatis-01-introduction/src/main/resources/CarMapper.xml"/>
      
```

6. 关于MyBatis的事务管理机制。（深度剖析）

```
   * 在mybatis-config.xml文件当中，可以通过以下的配置进行MyBatis的事务管理。
      <transactionManager type="JDBC"/>
   * type属性的值包括两个：
      JDBC（jdbc）
      MANAGED（managed）
      type后面的值，只有以上两个值可选，不区分大小写。
   * 在MyBatis中提供了两种事务管理机制：
      第一种：JDBC事务管理器
      第二种：MANAGED事务管理器
   * JDBC事务管理器
      MyBatis框架自己管理事务，自己采用原生的JDBC代码去管理事务。
         conn.setAutoCommit(false); 开启事务。
         ... 业务处理 ...
         conn.commit(); 手动提交事务。
      使用JDBC事务管理器的话，底层创建的事务管理器对象：JdbcTransaction对象。
      如果你编写的代码是下面的代码：
         SqlSession sqlSession = sqlSessionFactory.openSession(true);
         表示没有开启事务。因为这种方式压根不会执行conn.setAutoCommit(false);
         在JDBC事务中，没有执行conn.setAutoCommit(false);那么autoCommit就是true。
         如果autoCommit是true，就表示没有开启事务。只要执行任意一条DML语句就提交一次。
      
   * MANAGED事务管理
      MyBatis不再负责事务的管理了。事务管理交给其它容器来负责。例如：Spring。
      
      对于当前的单纯只有MyBatis的情况下，如果配置为：MANAGED，那么事务这块是没人管的。
      没有人管理事务表示事务压根没有开启。
      
   * JDBC中的事务：
      如果你没有在JDBC代码中执行：conn.setAutoCommit(false);的话，默认的autoCommit是true。
      
   * 重点：
      以后注意了，只要你的autoCommit是true，就表示没有开启事务。
      只有你的autoCommit是false的时候，就表示开启了事务。
```