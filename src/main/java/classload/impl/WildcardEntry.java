package classload.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * 通配符类路径，继承CompositeEntry
 */
public class WildcardEntry extends CompositeEntry {
    public WildcardEntry(String path) {
        super(toPathList(path));
    }

    /**
     *
     * @param wildcardPath
     * @return
     */
    private static String toPathList(String wildcardPath) {
        //去掉 *
        String baseDir = wildcardPath.replace("*", "");
        try {
            /**
             * 得到基础目录
             * 判断是不是文件
             * 得到路径字符串
             * 得到后缀问jar的文件
             * 将多个路径增加分隔符 在Windows下是以;分割
             */
            return Files.walk(Paths.get(baseDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    .collect(Collectors.joining(File.pathSeparator));
        } catch (IOException e) {
            return "";
        }
    }
}
