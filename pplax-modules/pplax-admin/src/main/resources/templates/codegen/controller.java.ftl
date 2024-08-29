package ${packageName};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.service.${className}Service;

import java.util.List;

/**
* @description ${tableName}表 controller
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("/${apiName}")
@Api(value="${tableName}表Controller", tags={"${tableComment}"})
public class ${className}Controller extends SuperController {

    private static final Logger log = LogManager.getLogger(${className}Controller.class);

    @Autowired
    private ${className}Service ${apiName}Service;

    @ApiOperation(value="删除", notes="删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "${apiName}Uid",value = "${tableName}表uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{${apiName}Uid}")
    public ResponseResult delete(@PathVariable("${apiName}Uid") String ${apiName}Uid) {
        ${apiName}Service.removeById(${apiName}Uid);
        return success();
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "${apiName}UidList",value = "${apiName}UidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> ${apiName}UidList) {
        ${apiName}Service.removeByIds(${apiName}UidList);
        return success();
    }

}
