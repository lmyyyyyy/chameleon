package cn.code.chameleon.pipeline;

import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.utils.FilePersistent;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-11 下午6:17
 */
public class FilePipeline extends FilePersistent implements Pipeline {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static final String DEFAULT_PATH = "/data/chameleon/";

    public FilePipeline() {
        setPath(DEFAULT_PATH);
    }

    public FilePipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(Results results, Task task) {
        //todo 对每个结果生成一个随机目录 String path = this.path + PATH_SPLIT + task.getUUID() + PATH_SPLIT;
        String path = this.path + PATH_SPLIT;
        try {
            //todo 对文件名进行加密 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(results.getRequest().getUrl()) + ".html")), Charset.defaultCharset()));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + results.getRequest().getUrl() + ".txt")), Charset.defaultCharset()));
            printWriter.println("url:\t" + results.getRequest().getUrl());
            for (Map.Entry<String, Object> entry : results.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
                    printWriter.println(entry.getKey() + ":");
                    for (Object obj : value) {
                        printWriter.println(obj);
                    }
                } else {
                    printWriter.println(entry.getKey() + ":\t" + entry.getValue());
                }
            }
            printWriter.close();
        } catch (IOException e) {
            LOGGER.error("write file to disk error", e);
        }
    }
}
