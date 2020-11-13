package com.yicj.study.mybatis;


import com.yicj.study.mapper.UserMapper;
import com.yicj.study.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MybatisTest {

    @Test
    void test1() throws IOException {
        String resource = "mybatis-config-h1.xml";
        Properties prop = new Properties() ;
        prop.setProperty("password","123") ;
        InputStream inputStream = Resources.getResourceAsStream(resource);
        new SqlSessionFactoryBuilder().build(inputStream,prop);
    }

    @Test
    void test2() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll() ;
            users.stream().forEach(user -> log.info("user : {}", user));
        }
    }



}
