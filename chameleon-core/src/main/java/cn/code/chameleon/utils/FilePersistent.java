package cn.code.chameleon.utils;

import java.io.File;

/**
 * @author liumingyu
 * @create 2018-04-11 下午7:34
 */
public class FilePersistent {

    protected String path;

    public static String PATH_SPLIT = "/";

    static {
        String property = System.getProperty("file.separator");
        PATH_SPLIT = property;
    }

    public void setPath(String path) {
        if (!path.endsWith(PATH_SPLIT)) {
            path += PATH_SPLIT;
        }
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public File getFile(String fullName) {
        checkAndMakeParentDirectory(fullName);
        return new File(fullName);
    }

    public void checkAndMakeParentDirectory(String fullName) {
        int index = fullName.lastIndexOf(PATH_SPLIT);
        if (index > 0) {
            String path = fullName.substring(0, index);
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }
}
