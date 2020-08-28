package com.yicj.study.parsing;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: GenericTokenParserTest
 * Description: TODO(描述)
 * Date: 2020/8/28 11:10
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class GenericTokenParserTest {


  private class VariableTokenHandler implements TokenHandler {
    private Map<String, String> variables = new HashMap<>();

    VariableTokenHandler(Map<String, String> variables) {
      this.variables = variables;
    }

    @Override
    public String handleToken(String content) {
      return variables.get(content);
    }
  }


  @Test
  void parse1(){
    String content =
          "SELECT * FROM t_action" +
          " WHERE `id`= ${id}" +
          " ORDER BY `actionTime`" ;
    VariableTokenHandler handler = new VariableTokenHandler(new HashMap<String, String>() {
      {
        put("id", "James");
        put("", "");
      }
    });
    GenericTokenParser parser = new GenericTokenParser("${", "}", handler) ;
    String retContent = parser.parse(content);
    log.info("retContent : {}", retContent);
  }
}