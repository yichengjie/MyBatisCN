package com.yicj.study.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

//https://www.jianshu.com/p/b1ad2f1d3e3e
public class Dao<T> {
    public Class clazz;

    public Dao() {
		Type superclass = getClass().getGenericSuperclass();
		if (superclass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) superclass;
			Type[] typeArray = parameterizedType.getActualTypeArguments();
			if (typeArray != null && typeArray.length > 0) {
			      clazz=(Class)typeArray[0];
			}
		}
	}

	public T query(){
		Class<? extends Dao> aClass = this.getClass();

		System.out.println("clazz : " + clazz);
		return null ;
	}

	static class User{}
	static class UserDao extends Dao<User>{}
	static class HelloDao extends Dao{}

	public static void main(String[] args) {
//    	Dao<User> dao = new UserDao() ;
//    	dao.query();

//		Dao dao2 = new HelloDao() ;
//		dao2.query();


		Dao<Integer> dao3 = new Dao<>();
		dao3.query() ;

	}
}