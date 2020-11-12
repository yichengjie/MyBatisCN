package com.yicj.study.mybatis;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MybatisTest {

    @Test
    void test1() throws IOException {
        String resource = "mybatis-config-h1.xml";
        Properties prop = new Properties() ;
        prop.setProperty("password","123") ;
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,prop);
    }
}
