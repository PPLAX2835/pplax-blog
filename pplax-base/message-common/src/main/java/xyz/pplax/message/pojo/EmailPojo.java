package xyz.pplax.message.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.pplax.core.constant.FieldLengthConstant;
import xyz.pplax.core.valid.Delete;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;

import javax.validation.constraints.NotNull;

/**
 * email数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailPojo {

    /**
     * 唯一uid，不能为null，主键
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 此邮箱配置对应哪个用户 可以为null
     */
    @NotNull
    private Long userUid;

    /**
     * 邮箱主机 可以为null
     * <p>length < 25</p>
     */
    @Length(max = FieldLengthConstant.EMAIL_NUMBER,message = "邮箱-长度不能超过25")
    private String emailHost;

    /**
     * 邮箱授权码 可以为null
     * <p>length < 50</p>
     */
    @Length(max = FieldLengthConstant.EMAIL_PASSWORD,message = "邮箱-配置中的密码不能超过{max}")
    private String emailPassword;

    /**
     * 邮箱协议 可以为null
     * <p>length < 10</p>
     */
    @Length(max = FieldLengthConstant.EMAIL_PROTOCOL,message = "邮箱-配置中的协议不能超过{max}")
    private String protocol;

    /**
     * 邮件服务的端口
     */
    private Integer port;

    /**
     * 邮箱号
     */
    @NotNull(groups = {Insert.class})
    @Length(max = FieldLengthConstant.EMAIL_NUMBER)
    @javax.validation.constraints.Email(message = "邮件的邮箱号不满足要求")
    private String email;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}