package cn.code.chameleon.monitor.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @create 2018-01-21 下午2:59
 */
@Data
public class ObjectEncodeDTO implements Serializable{
    private Object data;
    private String className;
    private String encodingType;
}
