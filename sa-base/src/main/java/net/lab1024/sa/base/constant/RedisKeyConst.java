package net.lab1024.sa.base.constant;

/**
 * redis key 常量类
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2022-05-30 21:22:12
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
public class RedisKeyConst {

    public static final String SEPARATOR = ":";

    /**
     * 系统级别公用常量
     */
    public static class Support {

        public static final String FILE_PRIVATE_VO = "file:private:";

        public static final String SERIAL_NUMBER_LAST_INFO = "serial-number:last-info";

        public static final String SERIAL_NUMBER = "serial-number:";

        public static final String CAPTCHA = "captcha:";

        public static final String LOGIN_VERIFICATION_CODE = "login:verification-code:";

    }
    // **************************************************************************************
    //      不同功能的redis key请继承 RedisKeyConst 并且定义不同的内部类，以此表明含义并区分用途
    //              示例见 AdminRedisKeyConst、AdminCacheConst
    // **************************************************************************************
}
