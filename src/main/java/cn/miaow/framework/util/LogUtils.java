package cn.miaow.framework.util;

/**
 * 处理并记录日志文件
 *
 * @author miaow
 */
public class LogUtils {
    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
