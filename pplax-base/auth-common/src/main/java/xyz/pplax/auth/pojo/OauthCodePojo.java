package xyz.pplax.auth.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * oauth_code数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OauthCodePojo {

    /**
     * 
     */
    @Schema(title = "")
    private String code;

    /**
     * 
     */
    @Schema(title = "")
    private Long authentication;

}