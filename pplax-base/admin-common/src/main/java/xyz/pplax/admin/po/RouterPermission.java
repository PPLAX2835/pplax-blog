package xyz.pplax.admin.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 路由权限
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "au_router_permission数据表的实体类")
public class RouterPermission implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long uid;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long adminRouterUid;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long permissionUid;

	private List<Long> adminRouterUidList;

	private List<Long> permissionUidList;
}