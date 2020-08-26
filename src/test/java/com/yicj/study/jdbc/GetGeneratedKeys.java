package com.yicj.study.jdbc;

import com.yicj.study.mapper.UserMapper;
import com.yicj.study.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.*;

/**
 * ClassName: GetGeneratedKeys
 * Description: TODO(描述)
 * Date: 2020/8/26 18:39
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class GetGeneratedKeys {

    @Test
    void test1() throws Exception{
      Connection conn = getConnection() ;
      String sql = "insert into user(username, password, roles) values(?, ? , ?)" ;
      PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, "yicj1");
      pstmt.setString(2,"123");
      pstmt.setString(3, "test");
      int i = pstmt.executeUpdate();
      ResultSet rs = pstmt.getGeneratedKeys();
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      log.info("columnCount :{}", columnCount);
      while (rs.next()){
        int anInt = rs.getInt(1);
        log.info("ant int : {}", anInt);
      }
      rs.close();
      pstmt.close();
      conn.close();
    }


    public Connection getConnection() throws Exception{
        String url = "jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT" ;
        String user = "root" ;
        String password = "root" ;
        Class.forName("com.mysql.cj.jdbc.Driver") ;
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection ;
    }

    @Test
    void test2() throws Exception{
      String resource = "mybatis-config.xml";
      InputStream inputStream = Resources.getResourceAsStream(resource);
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      User user = new User() ;
      user.setUsername("yicj3");
      user.setPassword("123");
      user.setRoles("admin");
      try (SqlSession session = sqlSessionFactory.openSession()) {
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.insert(user);
        log.info("user : {}", user);
      }

    }

}