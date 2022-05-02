package classload.impl;

import classload.Entry;

import java.io.IOException;
import java.nio.file.*;

/**
 * zip/jar文件形式类路径
 */
public class ZipEntry implements Entry {
    //zip/jar的绝对路径
    private Path absolutePath;

    public ZipEntry(String path) {
        //获取绝对路径
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    /**
     * 从ZIP文件中提取class文件
     * @param className class文件名
     * @return
     * @throws IOException
     */
    @Override
    public byte[] readClass(String className) throws IOException {
        //构造一个新的 FileSystem以访问文件的内容作为文件系统。
        try (FileSystem zipFs = FileSystems.newFileSystem(absolutePath, null)) {
            return Files.readAllBytes(zipFs.getPath(className));
        }
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}
