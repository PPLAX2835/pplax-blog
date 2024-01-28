package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.feign.file.FileFeignClient;
import xyz.pplax.pplaxblog.commons.base.global.BaseRegexConf;
import xyz.pplax.pplaxblog.xo.dto.BlogSortDto;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.TagService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 测试 RestApi
 */
@RestController
@RequestMapping("${pplax.api.basePath}/admin/adminTest")
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

	@Value("${pplax.api.basePath}")
	private String basePath;

	@RequestMapping(value = "/nacosconftest")
	public String nacosConfTest() {
		return basePath;
	}

	@RequestMapping(value = "/feignServerTest")
	public String feignServerTest(@RequestParam("param") String param) {
		return "feignServerTest";
	}

	@RequestMapping(value = "/regextest")
	public String regexTest() {
		String str = "[401] during [POST] to [http://auth-server/oauth/token?client_id=admin&client_secret=admin123456&grant_type=password&username=lucky&password=12345] [AuthFeignClient#getToken(String,String,String,String,String)]: [{\"error\":\"unauthorized\",\"error_description\":\"密码错误\"}]";
		String regex = BaseRegexConf.JSON_REGEX;

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		if (matcher.find()) {

			return matcher.group(0);
		} else {
			return "匹配失败";
		}
	}


	@Autowired
	private FileFeignClient fileFeignClient;

	@RequestMapping(value = "/fileUploadFeignTest")
	public String upload(@RequestParam("file") MultipartFile[] files) throws Exception {

		for (MultipartFile multipartFile : files) {
			log.error(multipartFile.getOriginalFilename());
		}

		fileFeignClient.upload(files);
		return fileFeignClient.upload(files);
	}


	@RequestMapping(value = "/oauthUserInfoTest")
	public String oauthUserInfoTest(Authentication authentication) throws Exception {
		Object principal = authentication.getPrincipal();


		System.out.println(JSON.toJSONString(authentication.getCredentials()));
		System.out.println(JSON.toJSONString(authentication.getAuthorities()));
		System.out.println(JSON.toJSONString(authentication.getDetails()));


		return "JSON.toJSONString(principal)";
	}
}

