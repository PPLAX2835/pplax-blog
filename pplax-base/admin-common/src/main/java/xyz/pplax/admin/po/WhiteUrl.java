package xyz.pplax.admin.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 白名单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "white_url数据表的实体类")
public class WhiteUrl implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 
	 */
	@Schema(title = "")
	private Integer uid;

	/**
	 * 白名单地址
	 */
	@Schema(title = "白名单地址")
	private String url;

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

}