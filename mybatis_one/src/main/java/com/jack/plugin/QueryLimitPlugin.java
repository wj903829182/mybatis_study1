package com.jack.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * create by jack 2017/12/25
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class,//确定要拦截的对象
                        method = "prepare",//确定要拦截的方法
                        args = {Connection.class}//拦截方法的参数
                        )
        }
)
public class QueryLimitPlugin implements Interceptor{

    //Properties props = null;
    //默认限制查询返回行数
    private int limit;
    private String dbType;
    //限制表中间别名，避免表重名所以起得有点怪
    private static final String LMT_TABLE_NAME = "limit_Table_Name";

    /**
     * 代替拦截对象方法的内容
     * @param invocation 责任链对象
     * @return
     * @throws Throwable
     */

    public Object intercept(Invocation invocation) throws Throwable {
        //System.out.println("intercept()->before........................");
        //如果当前代理的是一个非代理对象，那么它就回调真实拦截对象的方法，如果不是它会调度下个插件代理对象的invoke方法
        //Object obj = invocation.proceed();
        //System.out.println("intercept()->after.....................");
        //取出被拦截对象
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStmtHandler = SystemMetaObject.forObject(statementHandler);
        //分离代理对象，从而形成多次代理，通过两次循环最原始的被代理类，mybatis使用的是jdk代理
        while (metaStmtHandler.hasGetter("h")) {
            Object object = metaStmtHandler.getValue("h");
            metaStmtHandler = SystemMetaObject.forObject(object);
        }

        //分离最后一个代理对象的目标类
        while (metaStmtHandler.hasGetter("target")) {
            Object object = metaStmtHandler.getValue("target");
            metaStmtHandler = SystemMetaObject.forObject(object);
        }

        //取出即将要执行的sql
        String sql = (String) metaStmtHandler.getValue("delegate.boundSql.sql");
        String limitSql;
        //判断参数是不是mysql数据库且sql有没有被插件重写过
        if ("mysql".equals(this.dbType) && sql.indexOf(LMT_TABLE_NAME) == -1) {
            //去掉前后空格
            sql = sql.trim();
            //将参数写入sql
            limitSql = "select * from ( "+sql+" )"+LMT_TABLE_NAME+" limit "+limit;
            //重要执行的sql
            metaStmtHandler.setValue("delegate.boundSql.sql",limitSql);
        }
        //调用原来对象的方法，进入责任链的下一层级
        return invocation.proceed();
    }

    /**
     * 生成对象的代理，这里常用的mybatis提供的Plugin类的wrap方法
     * @param target 被代理的对象
     * @return
     */
    public Object plugin(Object target) {
        //使用mybatis提供的Plugin类生成代理对象
        //System.out.println("plugin()调用生成代理对象");
        return Plugin.wrap(target,this);
    }

    /**
     *获取插件配置的属性，我们在mybatis的配置文件里面去配置
     * @param properties 是mybatis配置的参数
     */
    public void setProperties(Properties properties) {
        //this.props = properties;
        //System.out.println(props.getProperty("dbType"));
        String strLimit = properties.getProperty("limit","50");
        this.limit = Integer.parseInt(strLimit);
        //这里我们读取设置的数据库类型
        this.dbType = properties.getProperty("dbType","mysql");
    }
}
