package com.yicj.study.anno;

import org.junit.Before;
import org.junit.Test;

/**
 * ClassName: HelloworldTest
 * Description: TODO(描述)
 * Date: 2020/8/28 14:11
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class HelloworldTest {

  @Before
  public void before(){
    System.out.println("before junit test ...");
  }

  @Test
  public void test1(){

    System.out.println("hello world");
  }

}