1. 编写类继承TypeReference<T>
  ```java
  class AAA<T> extends TypeReference<T> {
      public void hello(){
          Type rawType = this.getRawType();
          System.out.println(rawType.getTypeName());
      }
  }
  ```
2. 编写具体类指定具体泛型
  ```java
  class BBB extends AAA<Person>{
  
  }
  class Person{}
  ```
3. 编写代码测试代码
  ```java
  public class TypeReferenceTest {
      @Test
      public void test1(){
          new BBB().hello();
      }
  }
  //打印出泛型的实际类型为Person
  //com.yicj.study.type.TypeReferenceTest$Person
  ```