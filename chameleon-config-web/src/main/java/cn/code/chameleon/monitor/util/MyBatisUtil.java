package cn.code.chameleon.monitor.util;

import cn.code.chameleon.monitor.dto.SqlParameterDTO;
import cn.code.chameleon.monitor.dto.SqlStatementDTO;
import com.google.common.collect.Lists;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-01-21 下午4:29
 */
public class MyBatisUtil {

    public static SqlStatementDTO getSQL4MapperMethod(JoinPoint joinPoint, SqlSessionFactory sessionFactory){
        if (joinPoint.getSignature() instanceof MethodSignature){
            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Class mapperInterface = methodSignature.getDeclaringType();
            MappedStatement mappedStatement = getMappedStatment(sessionFactory,mapperInterface,method);
            if (mappedStatement != null){
                MapperMethod.MethodSignature mybatisMethodSignature = new MapperMethod.MethodSignature(sessionFactory.getConfiguration(),mapperInterface,method);
                Object param = mybatisMethodSignature.convertArgsToSqlCommandParam(joinPoint.getArgs());
                param = wrapCollection(param);
                BoundSql sql = mappedStatement.getBoundSql(param);
                List<SqlParameterDTO> parameterDTOS = getParameters(mappedStatement,sql,param);
                SqlStatementDTO statementVO = new SqlStatementDTO();
                statementVO.setPrepareSql(sql.getSql());
                statementVO.setParameters(parameterDTOS);
                return statementVO;
            }
        }
        return null;
    }

    private static MappedStatement getMappedStatment(SqlSessionFactory sessionFactory, Class mapperInterface, Method method) {
        return sessionFactory.getConfiguration().getMappedStatement(String.format("%s.%s",mapperInterface.getName(),method.getName()));
    }

    private static Object wrapCollection(final Object object) {
        if (object instanceof Collection) {
            DefaultSqlSession.StrictMap<Object> map = new DefaultSqlSession.StrictMap<Object>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            return map;
        } else if (object != null && object.getClass().isArray()) {
            DefaultSqlSession.StrictMap<Object> map = new DefaultSqlSession.StrictMap<Object>();
            map.put("array", object);
            return map;
        }
        return object;
    }

    private static List<SqlParameterDTO> getParameters(MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        List<SqlParameterDTO> parameters = Lists.newArrayList();
        org.apache.ibatis.session.Configuration configuration = mappedStatement.getConfiguration();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    SqlParameterDTO parameterDTO = new SqlParameterDTO();
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }
                    parameterDTO.setValue(value);
                    parameterDTO.setJdbcTypeCode(jdbcType != null ? jdbcType.ordinal() : -1);
                    parameterDTO.setJdbcTypeName(jdbcType != null ? jdbcType.name() : "");
                    parameters.add(parameterDTO);
                }
            }
        }
        return parameters;
    }
}
