package com.yicj.study.reflection;

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
    public void computeIfAbsent(){
        String name = "test";
        Map<String, List<String>> map = new HashMap<>() ;
        map.computeIfAbsent(name, k -> new ArrayList<>()) ;
        map.putIfAbsent("name", new ArrayList<>()) ;

        Map<String, List<Method>> conflictingMethods = new HashMap<>() ;
        List<Method> list = conflictingMethods.computeIfAbsent(name, k -> new ArrayList<>());
        log.info("list ： {}", list);
    }


    @Test
    public void 获取类得实际参数话类型(){
        SubPersonService subPersonService = new SubPersonService() ;
        log.info("class equals : {}", subPersonService.getClass() == SubPersonService.class);
        ParameterizedType parameterizedType = (ParameterizedType)subPersonService.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (Type type: actualTypeArguments){
            log.info("actual type name : {}", type.getTypeName());
        }
    }


    @Test
    void 获取Class的类型参数(){
        Type[] types = HelloWorldTest.class.getTypeParameters();

        TypeVariable v1 = (TypeVariable) types[0] ;
        Type[] bs1 = v1.getBounds();
        log.info("bound size : {},  bound 0: {}, bound 1:{}", bs1.length,  bs1[0], bs1[1]);
        log.info("type variable name : {}", v1.getName());
        log.info("-------------分割线-----------------------------------");

        TypeVariable v2 = (TypeVariable) types[0] ;
        Type[] bs2 = v1.getBounds();
        log.info("bound size : {},  bound 0: {}", bs2.length,  bs2[0]);
        log.info("type variable name : {}", v2.getName());
        log.info("-------------分割线-----------------------------------");
    }

    @Test
    void 获取Class的类型参数2(){
        TypeVariable<Class<PersonService>>[] typeParameters = PersonService.class.getTypeParameters();
        for (TypeVariable<Class<PersonService>> typeVariable : typeParameters) {
            log.info("---------------------------------------");
            log.info("type variable name : {}", typeVariable.getName());//K1和V；定义的泛型变量<>里面的内容
            Type[] bounds = typeVariable.getBounds();
            for (int i = 0 ; i < bounds.length ; i++) {
                log.info("bound[{}] : {}", i, bounds[i]);//class java.lang.Object
            }
            log.info("generic declaration ：{}", typeVariable.getGenericDeclaration());//null
        }
    }

    @Test
    void 获取字段的类型参数() throws NoSuchFieldException {
        Field field = PersonService.class.getField("key");
        TypeVariable typeVariable = (TypeVariable)field.getGenericType() ;
        log.info("type name : {}, typeName: {}" ,typeVariable.getName(), typeVariable.getTypeName());
        Type[] bounds = typeVariable.getBounds();
        for (Type bound: bounds){
            log.info("bound name :{}", bound.getTypeName());
        }
    }

    @Test
    void 获取方法返回值类型参数() throws NoSuchMethodException {
        Method hello = PersonService.class.getMethod("hello");
        TypeVariable typeVariable = (TypeVariable)hello.getGenericReturnType() ;
        log.info("type name : {}, typeName: {}" ,typeVariable.getName(), typeVariable.getTypeName());
        Type[] bounds = typeVariable.getBounds();
        for (Type bound: bounds){
            log.info("bound name :{}", bound.getTypeName());
        }
    }

    @Test
    void 获取字段实际类型_不可行() throws NoSuchFieldException {
        Field ttt = PersonService.class.getDeclaredField("ttt");
        // 上面或者下面这种都获取不到实际的参数类型
        //Field ttt = SubPersonService.class.getField("ttt");
        // 返回ParameterizedType对象
        ParameterizedType genericType = (ParameterizedType)ttt.getGenericType();
        Type[] actualTypeArguments = genericType.getActualTypeArguments();
        for (Type type: actualTypeArguments){
            log.info("type name : {} ,type class :{}",type.getTypeName(), type.getClass());
        }
    }

    @Test
    void 获取字段实际类型_可行() throws NoSuchFieldException {
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
    void 字段类型参数与Class的类型参数() throws NoSuchFieldException {
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
        public Map<K1,V> ttt ;
        public V hello(){
            return null ;
        }

        public void testRun() {
            System.out.println("key:" + key.getClass());
            System.out.println("value:" + value.getClass());
        }
    }

    public static class SubPersonService extends PersonService<String, Integer>{ }

    class Hello{
        public Map<String, List<String>> hello(){
            return null ;
        }
    }
}