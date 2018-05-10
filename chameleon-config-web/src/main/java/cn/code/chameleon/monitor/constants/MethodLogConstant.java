package cn.code.chameleon.monitor.constants;


import cn.code.chameleon.monitor.annotation.MapperMethod;

/**
 * @author liumingyu
 * @create 2018-01-21 下午3:03
 */
public class MethodLogConstant {
    public static String INSERT_METHOD_TYPE = "insert";

    public static String UPDATE_METHOD_TYPE = "update";

    public static String DELETE_METHOD_TYPE = "delete";

    public static String MAPPER_INTERFACE_POSTFIX = "Mapper";

    public static final MapperMethod.OperationType DEFAULT_OPERATION_TYPE = MapperMethod.OperationType.UPDATE;
}
