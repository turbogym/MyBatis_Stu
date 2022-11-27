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
            map.put("carNum", "1111");
            map.put("brand", "比亚迪汉");
            map.put("guidePrice", 10.0);
            map.put("produceTime", "2020-11-11");
            map.put("carType", "电车");
            
            insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
            
    Java程序中使用POJO类给SQL语句的占位符传值：
        Car car = new Car(null, "2222", "比亚迪秦", 30.0, "2020-11-11", "新能源");
        insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
        注意：占位符#{}，大括号里面写：POJO类的属性名
        如果占位符里面的值对不上，会出现什么问题？
            There is no getter for property named 'xyz' in 'class com.byonecup.mybatis.pojo.Car'
            MyBatis去找：Car类中的getXyz()方法去了，没找到，报错了。
            
            如何解决？
                可以在Car类中提供一个getXyz()方法，这样问题就解决了。
                
            通过这个测试，得出一个结论：
                严格意义上来说：如果使用POJO对象传递值的话，#{}这个大括号中到底写什么？
                    写的是get方法的方法名去掉get，然后将剩下的单词首字母变小写。
                    例如：getUsername() --> #{username}
                    
            也就是说MyBatis在底层给?传值的时候，先要获取值，怎么获取呢？
                调用了pojo对象的get方法。例如：car.getCarNum();
```

3. delete

```
    * 需求：根据id删除数据
      实现：
        int count = sqlSession.delete("deleteById", 17);
        <delete id="deleteById">
            delete from t_car where id = #{id}
        </delete>
        注意：如果占位符只有一个，那么#{}的大括号里可以随意。
        
```

4. update

```
    * 需求：根据id修改某条记录。
    
    实现：
        <update id="updateById">
            update t_car set car_num=#{carNum}, brand=#{brand}, guide_price=#{guidePrice}, produce_time=#{produceTime}, car_type=#{carType} where id=#{id}
        </update>
        
        Car car = new Car(18l, "8888", "保时捷911", 100.00, "2020-11-11", "燃油车");
        int count = sqlSession.update("updateById", car);
```

5. select（查一个，根据主键查询的话，返回的结果一定是一个。）

```
    * 需求：根据id查询。
    
    实现：
        <select id="selectById" resultType="com.byonecup.mybatis.pojo.Car">
            select * from t_car where id=#{id}
        </select>
        
        Object car = sqlSession.selectOne("selectById", 18);
        
    需要特别注意的是：
        select标签中resultType属性，这个属性用来告诉MyBatis查询结果集封装成什么类型的Java对象，需要告诉MyBatis。
        resultType通常写的是：全限定类名。
        
    Car{id=18, carNum='null', brand='保时捷911', guidePrice=null, produceTime='null', carType='null'}
    输出结果有点不对劲：
        id和brand属性有值。其它属性为null。
        
    carNum以及其它的这几个属性没有赋上值的原因是什么？
        select * from t_car where id=#{id}
        执行结果：
            +----+---------+--------------+-------------+--------------+-----------+
            | id | car_num | brand        | guide_price | produce_time | car_type  |
            +----+---------+--------------+-------------+--------------+-----------+
            | 18 | 8888    | 保时捷911    |      100.00 | 2020-11-11   | 燃油车    |
            +----+---------+--------------+-------------+--------------+-----------+
            1 row in set (0.00 sec)
        
            car_num、guide_price、produce_time、car_type这是查询结果的列名。
            这些列名和Car类中的属性名对不上。
            Car类的属性名：
            carNum、guidePrice、produceTime、carType
            
            那这个问题怎么解决呢？
                select语句查询的时候，查询结果集的列名是可以使用as关键字起别名的。
                select
                    id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType
                from t_car
                where id = #{id}
                
```

6. select（查所有的）

```
    <select id="selectAll" resultType="com.byonecup.mybatis.pojo.Car">
        select
            id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType
        from t_car
    </select>
    
    List<Object> cars = sqlSession.selectList("selectAll");
    
    注意：resultType还是指定要封装的结果集的类型。不是指定List类型，是指定List集合中元素的类型。
    selectList方法：MyBatis通过这个方法可以得知你需要一个List集合。它会自动给你返回一个List集合。
```