package cn.code.chameleon.monitor.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-01-21 下午2:58
 */
@Data
public class SqlStatementDTO implements Serializable{
    private String prepareSql;
    private List<SqlParameterDTO> parameters;
}
