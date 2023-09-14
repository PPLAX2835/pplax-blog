package xyz.pplax.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.auth.po.OauthClientDetails;

/**
 * oauth_client_details数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "oauth_client_details数据表的VO")
public class OauthClientDetailsVO extends OauthClientDetails {

}