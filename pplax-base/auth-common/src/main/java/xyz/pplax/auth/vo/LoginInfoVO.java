package xyz.pplax.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.auth.po.LoginInfo;

/**
 * login_info数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "login_info数据表的VO")
public class LoginInfoVO extends LoginInfo {

}