package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 站点配置
 * @author PPLAX
 * @date 2024-3-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_site_setting")
public class SiteSetting extends SuperEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 英文名
	 */
	private String nameEn;

	/**
	 * 中文名
	 */
	private String nameZh;

	/**
	 * 值
	 */
	private Object value;

	public SiteSetting() {}
}