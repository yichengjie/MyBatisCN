package com.yicj.study.type;

import org.apache.ibatis.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

/**
 * ClassName: TypeReferenceTest
 * Description: TODO(描述)
 * Date: 2020/8/21 9:29
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class TypeReferenceTest {


    @Test
    public void test1(){

        new BBB().hello();
    }

    class AAA<T> extends TypeReference<T> {
        public void hello(){
            Type rawType = this.getRawType();
            System.out.println(rawType.getTypeName());
        }
    }

    class BBB extends AAA<Person>{

    }

    class Person{}


}