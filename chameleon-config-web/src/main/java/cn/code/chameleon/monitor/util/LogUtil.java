package cn.code.chameleon.monitor.util;


import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import cn.code.chameleon.monitor.pojo.ServiceLog;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author liumingyu
 * @create 2018-01-21 下午4:02
 */
public class LogUtil {

    /**
     * 当前service日志栈
     */
    public static ThreadLocal<Stack<ServiceLog>> currentServiceLog = new ThreadLocal<Stack<ServiceLog>>() {
        @Override
        protected Stack<ServiceLog> initialValue() {
            return new Stack<>();
        }
    };

    /**
     * 总记录
     */
    public static ThreadLocal<List<ServiceLogWithBLOBs>> serviceLogList = new ThreadLocal<List<ServiceLogWithBLOBs>>() {
        @Override
        protected List<ServiceLogWithBLOBs> initialValue() {
            return new ArrayList<>();
        }
    };

    /**
     * mapper日志集合
     */
    public static ThreadLocal<List<MapperLogWithBLOBs>> mapperThreadLocal = new ThreadLocal<List<MapperLogWithBLOBs>>() {
        @Override
        protected List<MapperLogWithBLOBs> initialValue() {
            return new LinkedList<>();
        }
    };

    /**
     * service日志入栈
     *
     * @param runServiceLog
     */
    public static void pushCurrentServiceLog(ServiceLogWithBLOBs runServiceLog) {
        if (!currentServiceLog.get().isEmpty()) {
            ServiceLog top = currentServiceLog.get().peek();
            runServiceLog.setTop(false);
            if (top.getId() == null) {
                runServiceLog.setParentId(0L);
            } else {
                runServiceLog.setParentId(top.getId());
            }
            runServiceLog.setParent(top);
        } else {
            runServiceLog.setTop(true);
            runServiceLog.setParentId(0L);
        }
        currentServiceLog.get().push(runServiceLog);
        serviceLogList.get().add(runServiceLog);
        runServiceLog.setMapperStartIndex(mapperThreadLocal.get().size());
    }

    /**
     * 获取当前serivce日志的栈顶元素
     *
     * @return
     */
    public static ServiceLog peekCurrentServiceLog() {
        if (currentServiceLog.get().isEmpty()) {
            return null;
        }

        return currentServiceLog.get().peek();
    }

    /**
     * 将mapper日志存入集合
     *
     * @param runMapperLog
     * @return
     */
    public static int pushRunMapperLog(MapperLogWithBLOBs runMapperLog) {
        if (!isServiceLogStackEmpty()) {
            runMapperLog.setParent(peekCurrentServiceLog());
        }
        mapperThreadLocal.get().add(runMapperLog);
        return mapperThreadLocal.get().size();
    }

    /**
     * 获取mapper日志集合
     *
     * @return
     */
    public static List<MapperLogWithBLOBs> getRunMapperLogList() {
        return mapperThreadLocal.get();
    }

    /**
     * 清除
     */
    public static void clear() {
        ServiceLog runServiceLog = currentServiceLog.get().pop();
        if (currentServiceLog.get().isEmpty()) {
            serviceLogList.get().clear();
            mapperThreadLocal.get().clear();
        }
    }

    /**
     * 栈是否为空
     *
     * @return
     */
    public static boolean isServiceLogStackEmpty() {
        return currentServiceLog.get().isEmpty();
    }
}
