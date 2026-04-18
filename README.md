后台文档：
https://smartadmin.vip/views/doc/back/ApiEncryptDecrypt.html

Manager 层：通用业务处理层，它有如下特征：

    对第三方平台封装的层，预处理返回结果及转化异常信息；
    对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；
    封装重复的业务逻辑，如批量操作、事务管理等。
    与 DAO 层交互，对多个 DAO 的组合复用。
注意事项：
    1、该 Manager 层不允许互相调用
    2、可以实现 ServiceImpl 或者 BaseService 类


代码规范：
1、业务代码层级与代码结构
    1.默认所有业务代码都再 module 包下，每个功能模块都有自己的包，包名与功能模块名相同。每个独立的业务都有自己的包，例如订单、支付、快递单号等
    2.每个业务包下都有自己的 controller、service、mapper 等包。新设立 manager 层（经理层），其定义除了名字与service层一致。都添加@Service注解
        作用是用于提出重复的业务逻辑、涉及事务的批量操作、对多项数据处理（缓存）

2、常量的使用

3、枚举的使用
位置：与常量的位置相同，置于 constant 包下
创建：必须继承BaseEnum类
相关操作
    1.比较值相同：使用BaseEnum继承来的 .equalsValue() 方法
    2.根据参数获取枚举类的实例：SmartEnumUtil.getEnumByValue()

4、谨慎使用事务
    1.禁止在类上加@Transactional 注解，只能在方法上加。
    2.事务的范围要小，避免事务的嵌套。
    3.尽量将需要事务的内容提出到manager层（经理层），在service层（服务层）中只负责业务逻辑的处理，不负责事务的管理。


--- 错误码
错误码按照功能划分，每个功能模块都有自己的错误枚举类，并且实现 ErrorCode 接口

如何添加新的错误码枚举类
1、于 sa-base\src\main\java\net\lab1024\sa\base\common\code 包下创建新的枚举类，实现 ErrorCode 接口
2、在枚举类中定义新的错误码，每个错误码都有一个唯一的数值和一个描述
3、在 sa-base\src\main\java\net\lab1024\sa\base\common\code\ErrorCode.java 文件中添加新的错误码枚举类的实例
4、将level等级设置为 对应的ErrorCode接口中的常量，业务通常使用 business 等级，系统错误使用 system 等级，用户相关错误使用 user 等级。如需拓展则可以在 ErrorCode 接口中添加新的常量。
示例如下

```
public enum SystemErrorCode implements ErrorCode {

    /**
     * 系统错误
     */
    SYSTEM_ERROR(10001, "系统似乎出现了点小问题"),

    ;

    private final int code;

    private final String msg;

    private final String level;

    SystemErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = LEVEL_SYSTEM;
    }

}
```
5、错误码由 开头的顺序+后四位错误码组成 ，其中开头的顺序要保证类级别唯一（每个类都有且仅有唯一的错误码顺序）且同一枚举下相同。
例如系统相关错误为 10001-19999，未预期到的错误为 20001-29999，用户相关错误为 30001-39999。订单相关的为 40001-49999。快递单号的为 50001-59999 等等，可向后拓展。

6、注册新的错误码枚举类需要在 ErrorCodeRegister 类中进行注册，否则禁止使用。
-- 系统启动时会在该类中对所有错误码进行范围校验与唯一性校验，若不符合规则就无法启动，因此错误码必须全局唯一，不能重复。

重要技术点
1、异常

