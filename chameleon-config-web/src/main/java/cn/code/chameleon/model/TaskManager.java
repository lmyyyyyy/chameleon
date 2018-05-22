package cn.code.chameleon.model;

import cn.code.chameleon.Spider;
import cn.code.chameleon.pojo.ChameleonTask;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liumingyu
 * @create 2018-05-15 下午3:30
 */
@Component
public class TaskManager {

    private Map<String, ChameleonTask> taskMap = new ConcurrentHashMap<>();

    public void put(String key, ChameleonTask value) {
        taskMap.put(key, value);
    }

    public void putList(List<ChameleonTask> tasks) {
        for (ChameleonTask task : tasks) {
            if (task == null) {
                continue;
            }
            taskMap.put(task.getJobGroup() + "-" + task.getId(), task);
        }
    }

    public ChameleonTask getTaskById(String key) {
        return taskMap.get(key);
    }

    public List<ChameleonTask> getRunningTasks() {
        List<ChameleonTask> runTasks = new ArrayList<>();
        for (ChameleonTask task : taskMap.values()) {
            if (task.getStatus() == Spider.Status.Running.getValue()) {
                runTasks.add(task);
            }
        }
        return runTasks;
    }

    public Map<String, ChameleonTask> get() {
        return taskMap;
    }

    public List<ChameleonTask> all() {
        List<ChameleonTask> tasks = Lists.newArrayListWithCapacity(taskMap.size());
        for (ChameleonTask task : taskMap.values()) {
            tasks.add(task);
        }
        return tasks;
    }

    public void remove(String uuid) {
        taskMap.remove(uuid);
    }

    public void removeAll() {
        taskMap.clear();
    }

    public void stop(String uuid) {
        taskMap.get(uuid).setStatus(Spider.Status.Stopped.getValue());
    }

    public void pause(String uuid) {
        taskMap.get(uuid).setStatus(Spider.Status.Pausing.getValue());
    }
}
