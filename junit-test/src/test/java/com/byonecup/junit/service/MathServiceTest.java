package com.byonecup.junit.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Title MyBatis_Stu MathServiceTest
 * @Description TODO
 * @Author Turbo
 * @Date 2022/11/27 19:07
 * @Version 1.0
 */
public class MathServiceTest {
    // 单元测试中有两个重要的概念：
    // 一个是：实际值（被测试的业务方法的真正执行结果）
    // 一个是：期望值（执行了这个业务方法之后，你期望的执行结果是多少）

    @Test
    public void testSum() {
        MathService mathService = new MathService();
        // 获取实际值
        int actual = mathService.sum(10, 30);
        // 期望值
        int expected = 40;
        // 加断言进行测试
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSub() {
        MathService mathService = new MathService();
        int actual = mathService.sub(40, 20);
        int expected = 20;
        Assert.assertEquals(expected, actual);
    }
}
