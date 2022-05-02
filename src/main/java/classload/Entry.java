package classload;

import classload.impl.CompositeEntry;
import classload.impl.DirEntry;
import classload.impl.WildcardEntry;
import classload.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

/**
 * 类路径接口
 */
public interface Entry {
    /**
     *  readClass（）方法负责寻找和加载class 文件
     * @param className class文件名
     * @return 类的字节码-字节数组
     * @throws IOException
     */
    byte[] readClass(String className) throws IOException;
    static Entry create(String path) {
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }

        if (path.endsWith(".jar") || path.endsWith(".JAR") ||
                path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }

        return new DirEntry(path);
    }
}
