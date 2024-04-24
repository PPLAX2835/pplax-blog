package xyz.pplax.pplaxblog.xo.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Controller基类
 */
public class SuperController {

	/**
	 * 获取登录用户uid
	 * @param httpServletRequest
	 * @return
	 */
	public String getUserUid(HttpServletRequest httpServletRequest) {
		String userUid = null;
		String authorization = httpServletRequest.getHeader("Authorization");
		if (!StringUtils.isEmpty(authorization)) {
			String accessToken = authorization.replace("Bearer ", "");
			String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
			JSONObject jsonObject = JSON.parseObject(payloadByBase64);
			userUid = (String) jsonObject.get("uid");
		}

		return userUid;
	}

	/**
	 * 将对象转为json串
	 * @param object
	 * @return
	 */
	public String toJson(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 将json串转为map对象
	 * @return
	 */
	public HashMap toMap(String json) {
		return JSON.parseObject(json, HashMap.class);
	}

	/**
	 * 自定义响应
	 * @param responseCode
	 * @param data
	 * @return
	 */
	public String response(Integer responseCode, Object data) {
		return JSON.toJSONString(new ResponseResult(responseCode, data));
	}

	/**
	 * 自定义响应
	 * @param responseCode
	 * @param message
	 * @return
	 */
	public String response(Integer responseCode, String message) {
		return JSON.toJSONString(new ResponseResult(responseCode, message));
	}

	/**
	 * 自定义响应
	 * @param responseCode
	 * @param data
	 * @param message
	 * @return
	 */
	public String response(Integer responseCode, Object data, String message) {
		return JSON.toJSONString(new ResponseResult(responseCode, data, message));
	}

	/**
	 * 返回一个成功响应
	 * @return
	 */
	public String success() {
		return JSON.toJSONString(ResponseResult.success());
	}

	/**
	 * 返回一个成功响应且携带数据
	 * @return
	 */
	public String success(Object object) {
		return JSON.toJSONString(ResponseResult.success(object));
	}

}
