package com.yicj.study.xpath;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * ClassName: XpathApp
 * Description: TODO(描述)
 * Date: 2020/8/22 17:21
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class XpathApp {

    public static void main(String[] args) throws Exception {
        String resource = "com/yicj/study/xpath/info.xml";
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(resourceAsStream);

        XPathFactory factory = XPathFactory.newInstance() ;
        XPath xpath = factory.newXPath();


        XPathExpression compile = xpath.compile("/members/user[id=2]");
        System.out.println(compile.evaluate(doc));
    }
}