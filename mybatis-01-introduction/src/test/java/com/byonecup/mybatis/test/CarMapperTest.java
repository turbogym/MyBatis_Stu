package com.byonecup.mybatis.test;

import com.byonecup.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

/**
 * @Title MyBatis_Stu CarMapperTest
 * @Description TODO
 * @Author Turbo
 * @Date 2022/11/27 19:18
 * @Version 1.0
 */
public class CarMapperTest {
    @Test
    public void testInsertCar() {
        SqlSession sqlSession = null;
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            // 开启会话（底层会开启事务）
            sqlSession = sqlSessionFactory.openSession();
            // 执行SQL语句，处理相关业务。
            int count = sqlSession.insert("insertCar");
            System.out.println(count);
            // 执行到这里，没有发生任何异常，提交事务。终止事务。
            sqlSession.commit();
        } catch (Exception e) {
            // 最好回滚事务
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            // 关闭会话（释放资源）
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void testInsertCarByUtil() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.insert("insertCar");
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }
}
