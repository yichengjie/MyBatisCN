package com.yicj.study.io;

import org.apache.ibatis.io.VFS;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: VSFTest
 * Description: TODO(描述)
 * Date: 2020/8/21 21:14
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class VSFTest {


    @Test
    void test1() throws IOException {
        String path = getPackagePath("com.yicj.study") ;
        List<String> list = VFS.getInstance().list(path);
        list.forEach(System.out::println);
    }

    protected String getPackagePath(String packageName) {
        return packageName == null ? null : packageName.replace('.', '/');
    }
}