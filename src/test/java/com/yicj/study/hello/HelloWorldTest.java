package com.yicj.study.hello;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: HelloWorldTest
 * Description: TODO(描述)
 * Date: 2020/8/18 10:03
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class HelloWorldTest <K extends Integer & Map, V>{

    K key;
    V value;

    @Test
    public void test1(){

        System.out.println("test");
    }

    @Test
    public void test2(){
        String name = "test";
        Map<String, List<String>> map = new HashMap<>() ;
        map.computeIfAbsent(name, k -> new ArrayList<>()) ;
        map.putIfAbsent("name", new ArrayList<>()) ;


        Map<String, List<Method>> conflictingMethods = new HashMap<>() ;
        List<Method> list = conflictingMethods.computeIfAbsent(name, k -> new ArrayList<>());



    }


    @Test
    void test3() throws NoSuchMethodException {
        Method hello = Hello.class.getDeclaredMethod("hello");
        Type returnType = hello.getGenericReturnType();

        if (returnType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) returnType ;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
        }

        Type[] types = returnType.getClass().getTypeParameters();
        System.out.println("----> len: " + types.length);
        for(Type type : types){
            TypeVariable t = (TypeVariable)type;
            System.out.println(t.getGenericDeclaration());
        }

    }




    @Test
    void test4(){
        Type[] types = HelloWorldTest.class.getTypeParameters();
        for(Type type : types){
            TypeVariable t = (TypeVariable)type;
            System.out.println(t.getGenericDeclaration());
            int size = t.getBounds().length;
            System.out.println(t.getBounds()[size - 1]);
            System.out.println(t.getName() + "\n-------------分割线-------------");
        }
    }


    @Test
    void test5(){
        TypeVariable<Class<PersonService>>[] typeParameters = PersonService.class.getTypeParameters();
        System.out.println("len : " + typeParameters.length);
        for (TypeVariable<Class<PersonService>> typeVariable : typeParameters) {

            System.out.println("---------------------------------------");
            System.out.println(typeVariable.getName());//K1和V；定义的泛型变量<>里面的内容
            Type[] bounds = typeVariable.getBounds();
            System.out.println(bounds.length);//1
            for (Type t : bounds) {
                System.out.println(t);//class java.lang.Object
            }
            System.out.println(typeVariable.getGenericDeclaration());//null
        }
    }

    @Test
    void test6() throws NoSuchMethodException {
        Method hello = PersonService.class.getMethod("hello");
        Type returnType = hello.getReturnType() ;

        System.out.println("return type : " + returnType.getClass().getName());

        TypeVariable<? extends Class<? extends Type>>[] typeParameters = returnType.getClass().getTypeParameters();

        for (TypeVariable<? extends Class<? extends Type>> typeVariable : typeParameters) {
            System.out.println(typeVariable.getName());
        }

    }

    @Test
    void test7() throws NoSuchFieldException {
        Field ttt = PersonService.class.getDeclaredField("ttt");
        // 返回ParameterizedType对象
        Type genericType = ttt.getGenericType();
        System.out.println(genericType.getTypeName());
        // 返回TypeVariable
        Field key = PersonService.class.getDeclaredField("key");
        Type genericType1 = key.getGenericType();
        System.out.println(genericType1.getTypeName());

    }


    class PersonService<K1, V> {
        private K1 key;
        private V value;

        private Map<K1,V> ttt ;

        class User<K, V> {

        }

        public V hello(){

            return null ;
        }

        public void testRun() {
            System.out.println("key:" + key.getClass());
            System.out.println("value:" + value.getClass());
        }
    }

        class Hello{
        public Map<String, List<String>> hello(){
            return null ;
        }
    }

}