# 使用MyBatis完成CRUD

1. 什么是CRUD？

```
    C：Create增
    R：Retrieve查（检索）
    U：Update改
    D：Delete删
```

2. insert

```
    <insert id="insertCar">
        insert into t_car(id,car_num,brand,guide_price,produce_time,car_type)
        values(null,'1003','丰田霸道',30.0,'2000-10-11','燃油车')
    </insert>
    这样写的问题是？
        值 显然是写死到配置文件中的。
        这个在实际开发中是不存在的。
        一定是前端的form表单提交过来数据。然后将值传给sql语句。
        
    例如：JDBC的代码是怎么写的？
        String sql = "insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,?,?,?,?,?);"
        ps.setString(1, xxx);
        ps.setString(2, yyy);
        ps.setString(3, zzz);
        ...
        
    在JDBC当中占位符采用的是?，在MyBatis当中是什么？
        和?等效的写法是：#{}
        在MyBatis当中不能使用?占位符，必须使用#{}来代替JDBC当中的?
        
    Java程序中使用Map可以给SQL语句的占位符传值：
        Map<String, Object> map = new HashMap<>();
        map.put("k1", "1111");
        map.put("k2", "比亚迪汉");
        map.put("k3", 10.0);
        map.put("k4", "2020-11-11");
        map.put("k5", "电车");
        
        insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{k1},#{k2},#{k3},#{k4},#{k5})
        注意：#{这里写map集合的key，如果key不存在，获取的是null}
        
        一般map集合的key起名的时候要见名知意。
        
```