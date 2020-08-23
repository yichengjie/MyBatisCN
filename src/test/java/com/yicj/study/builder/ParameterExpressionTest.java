package com.yicj.study.builder;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.ParameterExpression;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: ParameterExpressionTest
 * Description: TODO(描述)
 * Date: 2020/8/23 9:12
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */

@Slf4j
public class ParameterExpressionTest {


    @Test
    void test1(){
        String content = "content = id, javaType= int, jdbcType=NUMERIC, typeHandler=DemoTypeHandler ;" ;

        Map<String, String> propertiesMap =  new ParameterExpression(content) ;

        Set<Map.Entry<String, String>> entries = propertiesMap.entrySet();

        entries.forEach(entry -> log.info("key :{}, value:{}", entry.getKey(), entry.getValue()));
    }

}