package cn.code.chameleon.monitor.util;

import cn.code.chameleon.monitor.dto.ObjectEncodeDTO;
import cn.code.chameleon.monitor.enums.EncodeTypeEnum;
import cn.code.chameleon.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-01-21 下午4:21
 */
public class ObjectEncodeUtil {
    public static String encodeMethodArgs(Object[] args) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        List<String> argStrList = args != null ? new ArrayList<>(args.length) : Lists.newArrayList();
        if (args != null) {
            for (Object object : args) {
                argStrList.add(encodeObject(object));
            }
        }
        if (CollectionUtils.isNotEmpty(argStrList)) {
            sb.append(String.join(",", argStrList));
        }
        sb.append(']');
        return sb.toString();
    }


    public static String encodeErrorMessage(Throwable throwable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        throwable.printStackTrace(printStream);
        String errMessage = byteArrayOutputStream.toString();
        printStream.close();
        return errMessage;
    }

    public static String encodeObject(Object object) throws JsonProcessingException {
        ObjectEncodeDTO objectEncodeVO = new ObjectEncodeDTO();
        if (object != null) {
            objectEncodeVO.setClassName(object.getClass().getName());
        } else {
            objectEncodeVO.setClassName("");
        }
        objectEncodeVO.setData(object);
        objectEncodeVO.setEncodingType(EncodeTypeEnum.JSON.getDesc());
        String result;
        try {
            result = JsonUtils.toJsonStrWithEmptyDefault(objectEncodeVO);
            if (result == null) {
                return "null";
            }
        } catch (Exception e) {
            objectEncodeVO.setEncodingType(EncodeTypeEnum.TOSTRING.getDesc());
            objectEncodeVO.setData(object != null ? object.toString() : "null");
            result = JsonUtils.toJsonStr(objectEncodeVO);
        }
        return result;
    }
}
