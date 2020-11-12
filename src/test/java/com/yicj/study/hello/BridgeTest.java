package com.yicj.study.hello;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class BridgeTest {

  interface SuperInter<T>{
    void print(T t) ;
    void hello(T t) ;
  }

  class SubClass implements SuperInter<String>{
    @Override
    public void print(String s) {

    }

    @Override
    public void hello(String s) {

    }
  }

  @Test
  public void test1(){
    //printMethodCount(SuperInter.class) ;
    printMethodCount(SubClass.class) ;
  }

  private void printMethodCount(Class<?> clazz){
    Method[] methods = clazz.getDeclaredMethods();
    Arrays.stream(methods).forEach(method -> {
      System.out.println("isBridge: " + method.isBridge() +" , " + method.toString());
    });
    //public abstract void com.yicj.study.hello.BridgeTest$SuperInter.print(java.lang.Object)
  }

  class A<T>{
    public T get(T a){
      // a 进行一些操作
      return a;
    }
  }

  class B extends A<String>{

    @Override
    public String get(String a) {
      // a 进行一些操作
      return a ;
    }
  }

  @Test
  public void test2(){
    A a = new B() ;
    a.get(new Object()) ;
  }

}
