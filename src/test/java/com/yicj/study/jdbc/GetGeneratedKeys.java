package com.yicj.study.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
}