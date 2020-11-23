package com.yicj.study.plugins;

import org.apache.ibatis.plugin.*;
import org.junit.Test;

public class PluginsTest {

  @Test
  public void test1(){
    InterceptorChain interceptorChain = new InterceptorChain() ;
    interceptorChain.addInterceptor(new MyInterceptor());

    IHello hello = (IHello)interceptorChain.pluginAll(new HelloImpl());
    hello.hello("yicj");
  }


  @Intercepts({
    @Signature(type = IHello.class, method = "hello", args = {String.class}),
  })
  class MyInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
      Object[] args = invocation.getArgs();
      System.out.println("-------------");
      return invocation.proceed();
    }
  }

  public interface IHello {
    void hello(String name) ;
  }

  class HelloImpl implements IHello{
    public void hello(String name){
      System.out.println("hello world : " + name);
    }
  }
}
