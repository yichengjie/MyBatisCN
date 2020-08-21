package com.yicj.study.reflection;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * ClassName: JsonParamParseTest
 * Description: TODO(描述)
 * Date: 2020/8/20 10:44
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class JsonParamParseTest {

    @Test
    void parseRequestParam(){
        String jsonContent = "{\"userInfo\":{\"lastUpdateDate\":\"2020/08/20\",\"lastUpdateUser\":\"yicj\"},\"data\":{\"dept\":\"软件工程\",\"studentNo\":\"10011\"}}" ;
        Class <StudentVo>clazz = StudentVo.class ;
        RequestData<StudentVo> requestData = JsonUtil.parse4Request(jsonContent, clazz);
        StudentVo vo = requestData.getData();
        System.out.println(vo);
    }

    public static class JsonUtil{
        public static <E> RequestData<E> parse4Request(String jsonContent, Class<E> clazz){
            RequestParam param = JSON.parseObject(jsonContent, RequestParam.class);
            String data = param.getData() ;
            E e = JSON.parseObject(data, clazz);
            return new RequestData<E>(param.getUserInfo(), e);
        }
    }
    @Data
    @AllArgsConstructor
    public static class RequestData<Vo>{
        private UserInfo userInfo ;
        private Vo data ;
    }

    @Data
    public static class RequestParam{
        private UserInfo userInfo ;
        private String data ;
    }

    @Data
    public static class UserInfo{
        private String lastUpdateUser ;
        private String lastUpdateDate ;
    }

    @Data
    public static class StudentVo{
        private String studentNo ;
        private String dept ;
    }

    /*   @Test
    void obtainJsonContent(){
        //
        UserInfo userInfo = new UserInfo() ;
        userInfo.setLastUpdateUser("yicj");
        userInfo.setLastUpdateDate("2020/08/20");
        //
        StudentVo studentVo = new StudentVo() ;
        studentVo.setStudentNo("10011");
        studentVo.setDept("软件工程");
        //
        RequestData requestData = new RequestData(userInfo, studentVo) ;
        String jsonContent = JSON.toJSONString(requestData);
        System.out.println(jsonContent);
    }*/
}

