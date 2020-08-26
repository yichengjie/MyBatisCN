package com.yicj.study.model;

import lombok.Data;

/**
 * ClassName: User
 * Description: TODO(描述)
 * Date: 2020/8/26 19:28
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Data
public class User {
  private Integer id ;
  private String username ;
  private String password ;
  private String roles ;
}