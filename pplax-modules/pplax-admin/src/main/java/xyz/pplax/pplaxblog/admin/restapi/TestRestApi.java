package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.dto.BlogSortDto;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.TagService;


/**
 * 测试 RestApi
 */
@RestController
@RequestMapping("${pplax.api.base-path}/test")
@Api(value="测试RestApi", tags={"TestRestApi"})
public class TestRestApi {

	@Autowired
	BlogService blogService;

	@Autowired
	TagService tagService;

	@Autowired
	BlogSortService blogSortService;

	private static final Logger log = LogManager.getLogger(TestRestApi.class);


	@PostMapping(value = "/dtoTest")
	@ApiOperation(value="dtoTest", notes="dtoTest", response = String.class)
	public String dtoTest(@RequestBody BlogSortDto blogSortDto) {

		log.info(JSON.toJSONString(blogSortDto));

		return "yes";
	}

}

