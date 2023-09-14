package xyz.pplax.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.admin.po.Navigation;

/**
 * navigation数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "navigation数据表的VO")
public class NavigationVO extends Navigation {

}