package com.fss.fsswms.base.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({
	@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}) 
})
public class MybatisLoggingInterceptor implements Interceptor {

	private static final Logger log = LoggerFactory.getLogger(MybatisLoggingInterceptor.class);
	
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement mappedStatement = (MappedStatement)args[0];
		log.debug("SQL ID : {}", mappedStatement.getId());
		return invocation.proceed();
	}
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	
	public void setProperties(Properties properties) {
	}
}
