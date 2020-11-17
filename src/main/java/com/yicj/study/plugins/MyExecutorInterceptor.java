package com.yicj.study.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class})
})
public class MyExecutorInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(MyExecutorInterceptor.class);
    private Logger sqlLogger = LoggerFactory.getLogger("mybatisSql") ;
    private static String [] officeIdNames = new String[]{"OFFICE_ID","OFFICEID","PK_OFFICE_ID"};
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            printSql(invocation) ;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return invocation.proceed();
    }

    /**
     *  打印sql
     * @param invocation
     */
    private void printSql(Invocation invocation){
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0] ;
        //0. sql参数获取
        Object parameter = null ;
        if (invocation.getArgs().length > 1){
            parameter = invocation.getArgs()[1] ;
        }
        //1. 获取sqlId
        String sqlId = mappedStatement.getId() ;
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        // 获取真实sql
        String sql = getSql(configuration, boundSql, sqlId, 0);
        sqlLogger.info("{}", sql);
    }

    private String getSql(
        Configuration configuration, BoundSql boundSql, String sqlId, long time){
        String sql = doGetSql(configuration, boundSql) ;
        StringBuilder str = new StringBuilder(100) ;
        str.append(sqlId) ;
        str.append("  :  ") ;
        str.append(sql) ;
        return str.toString() ;
    }

    private String getParameterValue(Object obj){
        String value = null ;
        if (obj instanceof String){
            value = "'"+obj.toString()+"'" ;
        }else if (obj instanceof Date){
            DateFormat format = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA
            ) ;
            value= "'"+format.format(obj)+"'" ;
        }else {
            if (obj != null){
                value = obj.toString() ;
            }else {
                value = "" ;
            }
        }
        return value ;
    }

    private String doGetSql(Configuration configuration, BoundSql boundSql){
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!parameterMappings.isEmpty() && parameterObject != null){
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())){
                sql = sql.replaceFirst("\\?",
                    Matcher.quoteReplacement(getParameterValue(parameterObject))) ;
            }else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject) ;
                for (ParameterMapping parameterMapping: parameterMappings){
                    String propertyName = parameterMapping.getProperty() ;
                    if (metaObject.hasGetter(propertyName)){
                        Object obj = metaObject.getValue(propertyName) ;
                        sql = sql.replaceFirst("\\?",
                            Matcher.quoteReplacement(getParameterValue(obj))) ;
                    }else if (boundSql.hasAdditionalParameter(propertyName)){
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                            Matcher.quoteReplacement(getParameterValue(obj))) ;
                    }else {
                        sql = sql.replaceFirst("\\?", "缺失") ;
                    }
                }
            }
        }
        return sql ;
    }
}
