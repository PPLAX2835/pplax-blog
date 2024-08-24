package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 请求日志表
 * @author PPLAX
 * @date Sat Aug 24 10:02:22 CST 2024
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_request_log")
public class RequestLog extends SuperEntity {

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
    * ip
    */
    private String ip;

    /**
    * 地址
    */
    private String address;

    /**
    * 请求接口耗时
    */
    private Long spendTime;

    /**
    * 请求参数
    */
    private String paramsJson;

    /**
    * 浏览器
    */
    private String browser;

    /**
    * 操作系统
    */
    private String accessOs;

    /**
    * 接口类型 0 管理后台接口 | 1 前台接口
    */
    private Integer type;

    @TableField(exist = false)
    private String path;

    @TableField(exist = false)
    private String method;
}
