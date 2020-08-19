package com.yicj.study.hello;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.TypeParameterResolver;
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
@Slf4j
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
        //获取到TypeVariable，参数化类型只能在申明参数化类型的类上获取
        Type genericType1 = key.getGenericType();
        System.out.println(genericType1.getTypeName());
        //

    }

    @Test
    void test8() throws NoSuchFieldException {
        Field field = SubPersonService.class.getField("key");
        //获取到TypeVariable，参数化类型只能在申明参数化类型的类上获取
        TypeVariable fieldTypeVariable = (TypeVariable)field.getGenericType();
        log.info("file type variable : {}", fieldTypeVariable);
        // 通过类获取类型参数与
        Type superType = SubPersonService.class.getGenericSuperclass();
        // 参数化类型 PersonService<String, Integer>
        ParameterizedType parentAsType = (ParameterizedType) superType ;
        Type[] actualTypeArguments = parentAsType.getActualTypeArguments();

        // 获取类的类型参数
        Class<?> parentAsClass = (Class<?>)parentAsType.getRawType();
        TypeVariable<?>[] typeVariables = parentAsClass.getTypeParameters();
        log.info("----------------------------------------------------");
        for (int i = 0 ; i < actualTypeArguments.length; i++){
            Type curActualType = actualTypeArguments[i] ;
            TypeVariable curTypeVariable = typeVariables[i] ;
            log.info("index = {}, actualType = {}, type variable: {} ", (i +1), curActualType.getTypeName(), curTypeVariable.getName());
            if (fieldTypeVariable == curTypeVariable){
                System.out.println("找到了字段的实际类型 : " + curActualType.getTypeName());
            }
        }
        log.info("-----------------通过工具获取实际类型------------------");
        Type type = TypeParameterResolver.resolveFieldType(field, SubPersonService.class);
        System.out.println(type.getTypeName());
    }


    @Test
    void 获取实际参数化类型(){
        Type superclass = SubPersonService.class.getGenericSuperclass();
        // superclass是ParameterizedType
        ParameterizedType parameterizedType = (ParameterizedType) superclass ;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (Type actualTypeArgument: actualTypeArguments){
            log.info("----> {}", actualTypeArgument.getTypeName());
        }
    }

    @Test
    void 获取类型参数() {
        // 注意这里不能用SubPersonService.class.getTypeParameters()
        TypeVariable<?>[] typeParameters = PersonService.class.getTypeParameters();
        for (TypeVariable typeVariable: typeParameters){
            log.info("--> {}",typeVariable);
        }
    }

    @Test
    void 获取类型参数2() throws NoSuchFieldException {
        // 获取类的类型参数
        Type superclass = SubPersonService.class.getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclass ;
        Class<?> rawType = (Class<?>)parameterizedType.getRawType();
        // 获取类的类型参数
        TypeVariable<? extends Class<?>>[] typeParameters = rawType.getTypeParameters();
        for (TypeVariable<? extends Class<?>> typeVariable : typeParameters){
            log.info("--> {}",typeVariable);
        }
    }

    @Test
    void test9() throws NoSuchFieldException {
        Field field = PersonService.class.getField("key");
        //获取到TypeVariable，参数化类型只能在申明参数化类型的类上获取
        TypeVariable fieldTypeVariable = (TypeVariable)field.getGenericType();
        log.info("file type variable : {}", fieldTypeVariable);
        // 获取类的类型参数
        TypeVariable<? extends Class<?>>[] typeParameters = PersonService.class.getTypeParameters();
        for (TypeVariable<? extends Class<?>> typeVariable : typeParameters){
            log.info("{} type variable is equals filed type variable :{}", typeVariable.getName() , (fieldTypeVariable == typeVariable));
        }
    }


   public static class PersonService<K1, V> {
        public K1 key;
        private V value;

        private Map<K1,V> ttt ;

        public V hello(){

            return null ;
        }

        public void testRun() {
            System.out.println("key:" + key.getClass());
            System.out.println("value:" + value.getClass());
        }
    }


    public static class SubPersonService extends PersonService<String, Integer>{


    }

    class Hello{
        public Map<String, List<String>> hello(){
            return null ;
        }
    }

}