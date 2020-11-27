package com.yicj.study.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import java.sql.Connection;

@Slf4j
@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class MyStatementHandlerPlugin implements Interceptor {

    // 拦截目标对象的目标方法的执行，将自定义逻辑写在该方法中
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("MyStatementHandlerPlugin ... intercept : " + invocation.getMethod());
        MetaObject metaObject = SystemMetaObject.forObject(invocation);
        log.info("当前拦截到的方法: {}", metaObject.getValue("target"));
        log.info("SQL语句: {}", metaObject.getValue("target.delegate.boundSql.sql"));
        log.info("SQL语句入参: {}", metaObject.getValue("target.delegate.parameterHandler.parameterObject"));
        log.info("SQL语句类型：{}", metaObject.getValue("target.delegate.parameterHandler.mappedStatement.sqlCommandType"));
        log.info("Mapper方法全路劲名: {}", metaObject.getValue("target.delegate.parameterHandler.mappedStatement.id"));
        // 修改sql语句
        String newSql = metaObject.getValue("target.delegate.boundSql.sql").toString() ; //这里可以修改sql
        metaObject.setValue("target.delegate.boundSql.sql", newSql);
        return invocation.proceed();
    }
}
