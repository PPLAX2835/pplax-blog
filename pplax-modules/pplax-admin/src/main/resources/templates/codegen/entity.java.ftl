package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description ${tableComment}
 * @author ${author}
 * @date ${date}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("${tableName}")
public class ${className} extends SuperEntity {

    private static final long serialVersionUID = 1L;

<#list attributes as attribute>
    /**
    * ${attribute.attributeComment}
    */
    private ${attribute.attributeType} ${attribute.attributeName};

</#list>
}
