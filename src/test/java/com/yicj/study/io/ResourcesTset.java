package com.yicj.study.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * ClassName: ResourcesTset
 * Description: TODO(描述)
 * Date: 2020/8/22 20:57
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class ResourcesTset {

    @Test
    void test1() throws IOException {
        String location = "com/yicj/study/io/db.properties" ;
        Properties properties = Resources.getResourceAsProperties(location);
        String username = properties.getProperty("jdbc.user");
        log.info("username :{}", username);
    }
}