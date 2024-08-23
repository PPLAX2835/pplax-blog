package ${packageName};

import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
* @description ${tableName}表 controller
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("/${apiName}")
@Api(value="${tableName}Controller", tags={"${tableComment}"})
public class ${className}Controller extends SuperController {

    private static final Logger log = LogManager.getLogger(${className}Controller.class);


}
