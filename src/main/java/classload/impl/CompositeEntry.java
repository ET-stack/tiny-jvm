package classload.impl;

import classload.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompositeEntry implements Entry {
    private final List<Entry> entryList = new ArrayList<>();

    /**
     * 构造方法 实现命令风格 添加类路径
     * @param pathList
     */
    public CompositeEntry(String pathList){
        /**
         * File.pathSeparator指的是分隔连续多个路径字符串的分隔符，例如:
         * java   -cp   test.jar;abc.jar   HelloWorld
         * 就是指“;”
         */
        String[] paths = pathList.split(File.pathSeparator);
        for (String path: paths){
            entryList.add(Entry.create(path));
        }
    }

    /**
     * 依次调用每一个
     * 子路径的readClass（）方法，如果成功读取到class数据，返回数据即
     * 可；如果收到错误信息，则继续；如果遍历完所有的子路径还没有找到class文件，则返回错误
     * @param className
     * @return
     * @throws IOException
     */
    @Override
    public byte[] readClass(String className) throws IOException {
        for (Entry entry : entryList) {
            try {
                return entry.readClass(className);
            } catch (Exception ignored) {
                //ignored
            }
        }
        throw new IOException("class not found " + className);
    }

    /**
     * 调用每一个子路径的String方法，然
     * 后把得到的字符串用路径分隔符拼接起来即可
      * @return
     */
    @Override
    public String toString() {
        //给一个entryList大小的字符串数组，避免自动扩容
        String[] strs = new String[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            strs[i] = entryList.get(i).toString();
        }
        return String.join(File.pathSeparator, strs);
    }


}
