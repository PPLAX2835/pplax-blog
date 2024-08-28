package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 异常日志表
 * @author PPLAX
 * @date Wed Aug 28 20:48:20 CST 2024
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_exception_log")
public class ExceptionLog extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 操作用户uid
    */
    private String userUid;

    /**
    * 请求接口uid
    */
    private String menuUid;

    /**
    * 请求端点
    */
    private String endpoint;

    /**
    * ip
    */
    private String ip;

    /**
    * 地址
    */
    private String address;

    /**
    * 参数
    */
    private String paramsJson;

    /**
    * 发生异常的类名
    */
    private String className;

    /**
    * 发生异常的方法名
    */
    private String methodName;

    /**
    * 异常对象json格式
    */
    private String exceptionJson;

    /**
    * 异常简单信息,等同于e.getMessage
    */
    private String exceptionMessage;

    @TableField(exist = false)
    private Menu menu;

    @TableField(exist = false)
    private User user;

}
