package com.yicj.study.mybatis;

import com.yicj.study.mapper.UserMapper;
import com.yicj.study.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest2 {

  @Test
  void testInsert() throws IOException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    User user = User.builder()
      .username("李四")
      .password("123")
      .roles("users")
      .build() ;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statementId = "com.yicj.study.mapper.UserMapper.insert" ;
      session.insert(statementId, user) ;
      System.out.println("user : " + user);
    }
  }

}
