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
 * 用户角色表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "user_role数据表的实体类")
public class UserRoleRelationship implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long uid;

	/**
	 * 角色uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "角色uid")
	private Long roleUid;

	/**
	 * 用户uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "用户uid")
	private Long userUid;

}