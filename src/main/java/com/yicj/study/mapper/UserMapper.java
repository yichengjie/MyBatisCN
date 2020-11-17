package com.yicj.study.mapper;

import com.yicj.study.model.User;

import java.util.List;

/**
 * ClassName: UserMapper
 * Description: TODO(描述)
 * Date: 2020/8/26 19:27
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface UserMapper {
   void insert(User user) ;
   List<User> selectByParam(Integer id) ;
}