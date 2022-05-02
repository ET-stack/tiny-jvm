package classload.impl;

import classload.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 目录形式的类路径
 */
public class DirEntry implements Entry {
    private Path absolutePath;

    public DirEntry(String path){
        //获取绝对路径
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    /**
     * 把目录和class文件名拼成一个完整的路径
     * @param className class文件名
     * @return 一个完整的class绝对路径
     * @throws IOException
     */
    @Override
    public byte[] readClass(String className) throws IOException {
        return Files.readAllBytes(absolutePath.resolve(className));
    }

    /**
     * 返回路径字符串
     * @return
     */
    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}
