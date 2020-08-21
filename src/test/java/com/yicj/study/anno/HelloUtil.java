package com.yicj.study.anno;

/**
 * ClassName: HelloUtil
 * Description: TODO(描述)
 * Date: 2020/8/21 22:05
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class HelloUtil {


    static class FindByNameListener implements IterListener{
        @Override
        public void listener() {

        }
    }

    static class FindByTypeListener implements IterListener{
        @Override
        public void listener() {

        }
    }


    public static void findByName(){
        //a
        find(new FindByNameListener());
        //d
    }

    public static void findByType(){
        // b
        find(new FindByTypeListener());
        //c
    }

    public static void find(IterListener listener) {
        // addd
        listener.listener();
        //ddfdsdf
    }

    interface IterListener{

        void listener() ;
    }

}