package xyz.pplax.article.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * category数据表的实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "category数据表的实体类")
public class Category implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 此类别的标题
	 */
	@Schema(title = "此类别的标题")
	private String title;

	/**
	 * 此类别的简介
	 */
	@Schema(title = "此类别的简介")
	private String summary;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private String createTime;

	/**
	 * 最后修改时间
	 */
	@Schema(title = "最后修改时间")
	private String updateTime;

	/**
	 * 此类别的封面图地址
	 */
	@Schema(title = "此类别的封面图地址")
	private String coverUrl;

	/**
	 * 1: 删除 ，0：未删除
	 */
	@Schema(title = "1: 删除 ，0：未删除")
	private Boolean delete;

	/**
	 * 用户的userUid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "用户的userUid")
	private Long userUid;

}