package cn.code.chameleon.monitor.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @create 2018-01-21 下午2:57
 */
@Data
public class SqlParameterDTO implements Serializable{
    private int jdbcTypeCode;
    private String jdbcTypeName;
    private Object value;
}
