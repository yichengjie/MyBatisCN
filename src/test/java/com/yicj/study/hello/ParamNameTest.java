package com.yicj.study.hello;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ParamNameUtil;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * ClassName: ParamNameTest
 * Description: TODO(描述)
 * Date: 2020/8/20 21:39
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class ParamNameTest {

    @Test
    void 获取参数注解() throws NoSuchMethodException {
        Method method = HelloWorld.class.getMethod("hello", String.class, Integer.class);
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        log.info("len1 : {}", parameterAnnotations.length);
        for (Annotation[] annos: parameterAnnotations){
            log.info("len2: {}", annos.length);
            for (Annotation anno : annos){
                log.info("----> {}", anno.annotationType());
            }
        }
    }

    @Test
    void 获取实际参数名() throws NoSuchMethodException {
        Method method = HelloWorld.class.getMethod("hello", String.class, Integer.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter: parameters){
            String name = parameter.getName();
            log.info("param name : {}", name);
        }
        List<String> paramNames = ParamNameUtil.getParamNames(method);
        paramNames.forEach(name -> log.info("name : " +name));
    }

}