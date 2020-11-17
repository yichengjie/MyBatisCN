1. 准备待解析xml文件nodelet_test.xml
    ```xml
    <employee id="${id_var}">
      <blah something="that"/>
      <first_name>Jim</first_name>
      <last_name>Smith</last_name>
      <birth_date>
        <year>1970</year>
        <month>6</month>
        <day>15</day>
      </birth_date>
      <height units="ft">5.8</height>
      <weight units="lbs">200</weight>
      <active>true</active>
    </employee>
    ```
2. 使用XPathParser解析xml内容
    ```text
    public void main(String [] args) throws Exception {
        try (InputStream inputStream = Resources.getResourceAsStream("nodelet_test.xml")) {
          XPathParser parser = new XPathParser(inputStream);
          Long year = parser.evalLong("/employee/birth_date/year") ;
          String id = parser.evalString("/employee/@id") ;
          log.info("year : {}, id : {}", year, id) ;
          // year: 1970, id: ${id_var}
        }
    }
    ```