package com.byonecup.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @Title MyBatis_Stu ConfigurationTest
 * @Description TODO
 * @Author Turbo
 * @Date 2022/11/28 06:21
 * @Version 1.0
 */
public class ConfigurationTest {
    @Test
    public void testEnvironment() throws IOException {
        // 获取SqlSessionFactory对象（采用默认的方式获取）
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 这种方式就是获取的默认环境
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int count = sqlSession.insert("car.insertCar");
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();

        // 这种方式就是通过环境idl来使用指定的环境
        SqlSessionFactory sqlSessionFactory1 = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "development");
        SqlSession sqlSession1 = sqlSessionFactory1.openSession();
        int count1 = sqlSession1.insert("insertCar");
        System.out.println(count1);
        sqlSession.commit();
        sqlSession.close();
    }
}
