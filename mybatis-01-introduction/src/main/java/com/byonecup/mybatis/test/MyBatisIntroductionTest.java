package com.byonecup.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Title MyBatis_Stu MyBatisIntroductionTest
 * @Description TODO
 * @Author Turbo
 * @Date 2022/11/27 13:27
 * @Version 1.0
 */
public class MyBatisIntroductionTest {
    public static void main(String[] args) throws IOException {
        // 获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 获取SqlSessionFactory对象
//        InputStream is = Resources.getResourceAsStream("mybatis-config.xml"); // Resources.getResourceAsStream默认就是从类的根路径下开始查找资源
//        InputStream is = Resources.getResourceAsStream("com/mybatis.xml");
//        InputStream is = new FileInputStream("/Users/turbo/Desktop/Projects/JavaProjects/MyBatis_Stu/mybatis-01-introduction/src/main/resources/mybatis-config.xml");
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is); // 一般情况下，一个数据库对应一个SqlSessionFactory对象。

        // 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession(); // 如果使用的事务管理器是JDBC的话，底层实际上会执行：conn.commit(false);
        // 以下这种方式实际上是不建议的，因为没有开启事务。
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        // 执行SQL语句
        int count = sqlSession.insert("insertCar");

        System.out.println("插入了" + count + "条记录。");

        // 手动提交
        sqlSession.commit(); // 如果使用的事务管理器是JDBC的话，底层实际上还是会执行conn.commit();
    }
}
