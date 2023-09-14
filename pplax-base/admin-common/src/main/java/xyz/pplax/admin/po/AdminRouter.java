package xyz.pplax.admin.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后台管理路由的实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "admin_router数据表的实体类")
public class AdminRouter implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 标题
	 */
	@Schema(title = "标题")
	private String title;

	/**
	 * 作为单级路由的父级路由布局组件
	 */
	@Schema(title = "作为单级路由的父级路由布局组件")
	private String singleLayout;

	/**
	 * 需要登录权限
	 */
	@Schema(title = "需要登录权限")
	private Boolean requiresAuth;

	/**
	 * 是否缓存
	 */
	@Schema(title = "是否缓存")
	private Boolean keepalive;

	/**
	 * 图标
	 */
	@Schema(title = "图标")
	private String icon;

	/**
	 * 使用本地svg作为的菜单和面包屑对应的图标(assets/svg-icon文件夹的的svg文件名)
	 */
	@Schema(title = "使用本地svg作为的菜单和面包屑对应的图标(assets/svg-icon文件夹的的svg文件名)")
	private String localIcon;

	/**
	 * 是否在菜单中隐藏
	 */
	@Schema(title = "是否在菜单中隐藏")
	private Boolean hide;

	/**
	 * 外链链接
	 */
	@Schema(title = "外链链接")
	private String href;

	/**
	 * 路由顺序，可用于菜单的排序
	 */
	@Schema(title = "路由顺序，可用于菜单的排序")
	private Integer order;

	/**
	 * 是否固定在tab卡不可关闭
	 */
	@Schema(title = "是否固定在tab卡不可关闭")
	private Boolean affix;

	/**
	 * 
	 */
	@Schema(title = "")
	private String createTime;

	/**
	 * 
	 */
	@Schema(title = "")
	private String updateTime;

	/**
	 * 路由名称
	 */
	@Schema(title = "路由名称")
	private String name;

	/**
	 * 路由路径
	 */
	@Schema(title = "路由路径")
	private String path;

	/**
	 * 组件
	 */
	@Schema(title = "组件")
	private String component;

	/**
	 * 该路由的子路由集合，使用,分隔开
	 */
	@Schema(title = "该路由的子路由集合，使用,分隔开")
	private String sonRouterUids;

	/**
	 * 父路由uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "父路由uid")
	private Long parentRouterUid;

}