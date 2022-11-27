package com.byonecup.mybatis.test;

import com.byonecup.mybatis.pojo.Car;
import com.byonecup.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Title MyBatis_Stu CarMapperTest
 * @Description TODO
 * @Author Turbo
 * @Date 2022/11/28 03:27
 * @Version 1.0
 */
public class CarMapperTest {
    @Test
    public void testInsertCar() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 这个对象我们先使用Map集合进行数据的封装。
        Map<String, Object> map = new HashMap<>();
//        map.put("k1", "1111");
//        map.put("k2", "比亚迪汉");
//        map.put("k3", 10.0);
//        map.put("k4", "2020-11-11");
//        map.put("k5", "电车");
        map.put("carNum", "1111");
        map.put("brand", "比亚迪汉");
        map.put("guidePrice", 10.0);
        map.put("produceTime", "2020-11-11");
        map.put("carType", "电车");

        // insert方法的参数：
        // 第一个参数：sqlId，从CarMapper.xml文件中复制。
        // 第二个参数：封装数据的对象。
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsertCarByPOJO() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 封装数据
        Car car = new Car(null, "2222", "比亚迪秦", 30.0, "2020-11-11", "新能源");
        int count = sqlSession.insert("insertCar", car);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.delete("deleteById", 17);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }
}
