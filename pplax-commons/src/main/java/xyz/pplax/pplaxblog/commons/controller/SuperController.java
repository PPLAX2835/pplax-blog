package xyz.pplax.pplaxblog.commons.controller;

import com.alibaba.fastjson.JSON;
import xyz.pplax.pplaxblog.commons.response.ResponseCode;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import java.util.HashMap;

/**
 * Controller基类
 */
public class SuperController {

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

	/**
	 * 返回一个失败响应，携带错误信息
	 * @return
	 */
	public String error(String message) {
		return JSON.toJSONString(ResponseResult.error(message));
	}

}
