<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.pplax.pplaxblog.xo.mapper.${className}Mapper">

    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="xyz.pplax.pplaxblog.xo.entity.${className}">
        <id column="uid" property="uid" />
        <#list columnConverters as columnConverter>
        <result column="${columnConverter.column}" property="${columnConverter.property}" />
        </#list>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        ${columnsStr}
    </sql>

</mapper>
