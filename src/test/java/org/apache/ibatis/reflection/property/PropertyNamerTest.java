
package org.apache.ibatis.reflection.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * ClassName: PropertyNamerTest
 * Description: TODO(描述)
 * Date: 2020/8/18 20:43
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class PropertyNamerTest {

    @Test
    void methodToProperty(){
        String [] methodNames = {"getName", "setName", "getAge"} ;
        for (String methodName: methodNames) {
            String fieldName = PropertyNamer.methodToProperty(methodName);
            log.info("field name : {}", fieldName);
        }
    }


    @Test
    void obtainMethod(){
        Method[] declaredMethods = User.class.getMethods() ;
        for (Method method : declaredMethods){
            log.info("--> {}, {}", method.getName(), method.getDeclaringClass() );
        }

    }


    @Setter
    @Getter
    class User{
        private String name ;
        private Integer age ;
    }
}