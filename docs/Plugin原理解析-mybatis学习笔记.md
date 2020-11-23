1. 编写业务接口及实现类
  ```java
  public interface IHello {
    void hello(String name) ;
  }
  
  class HelloImpl implements IHello{
    public void hello(String name){
      System.out.println("hello world : " + name);
    }
  }
  ```
2. 编写拦截器类
  ```java
  @Intercepts({
    @Signature(type = IHello.class, method = "hello", args = {String.class}),
  })
  class MyInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
      System.out.println("-------------");
      return invocation.proceed();
    }
  }
  ```
3. 编写单元测试
  ```java
  public class PluginsTest {
    @Test
    public void test1(){
      InterceptorChain interceptorChain = new InterceptorChain() ;
      interceptorChain.addInterceptor(new MyInterceptor());
  
      IHello hello = (IHello)interceptorChain.pluginAll(new HelloImpl());
      hello.hello("yicj");
    }
  }
  // 输出
  //-------------
  //hello world : yicj
  ```

