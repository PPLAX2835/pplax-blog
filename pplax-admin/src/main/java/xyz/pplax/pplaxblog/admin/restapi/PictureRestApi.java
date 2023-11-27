//package xyz.pplax.pplaxblog.admin.restapi;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import xyz.pplax.pplaxblog.admin.global.SQLConf;
//import xyz.pplax.pplaxblog.admin.global.SysConf;
//import xyz.pplax.pplaxblog.utils.ResultUtil;
//import xyz.pplax.pplaxblog.utils.StringUtils;
//import xyz.pplax.pplaxblog.xo.entity.Picture;
//import xyz.pplax.pplaxblog.xo.service.PictureService;
//import xyz.pplax.pplaxblog.base.enums.EStatus;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
///**
// * <p>
// * 图片表 RestApi
// * </p>
// */
//@RestController
//@RequestMapping("/picture")
//public class PictureRestApi {
//	@Autowired
//	PictureService pictureService;
//
//	private static Logger log = LogManager.getLogger(AdminRestApi.class);
//
//	@ApiOperation(value="获取图片列表", notes="获取图片列表", response = String.class)
//	@RequestMapping(value = "/getList", method = RequestMethod.GET)
//	public String getList(HttpServletRequest request,
//			@ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
//			@ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
//			@ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {
//
//		QueryWrapper<Picture> queryWrapper = new QueryWrapper<Picture>();
//		if(!StringUtils.isEmpty(keyword)) {
//			queryWrapper.like(SQLConf.PIC_NAME, keyword);
//		}
//
//		Page<Picture> page = new Page<>();
//		page.setCurrent(currentPage);
//		page.setSize(pageSize);
//		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
//		queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
//		IPage<Picture> pageList = pictureService.page(page, queryWrapper);
//		log.info("返回结果");
//		return ResultUtil.result(SysConf.SUCCESS, pageList);
//	}
//
//	@ApiOperation(value="增加图片", notes="增加图片", response = String.class)
//	@PostMapping("/add")
//	public String add(HttpServletRequest request,
//			@ApiParam(name = "fileUid", value = "图片UID",required = false) @RequestParam(name = "fileUid", required = false) String fileUid,
//			@ApiParam(name = "picName", value = "图片名称",required = false) @RequestParam(name = "picName", required = false) String picName,
//			@ApiParam(name = "pictureSortUid", value = "图片分类UID",required = false) @RequestParam(name = "pictureSortUid", required = false) String pictureSortUid) {
//
//		if(StringUtils.isEmpty(fileUid) || StringUtils.isEmpty(pictureSortUid)) {
//			return ResultUtil.result(SysConf.ERROR, "必填项不能为空");
//		}
//		Picture picture = new Picture();
//		picture.setFileUid(fileUid);
//		picture.setPicName(picName);
//		picture.setPictureSortUid(pictureSortUid);
//		picture.setStatus(EStatus.ENABLE);
//		picture.setCreateTime(new Date());
//		picture.setUpdateTime(new Date());
//		picture.insert();
//		return ResultUtil.result(SysConf.SUCCESS, "添加成功");
//	}
//
//	@ApiOperation(value="编辑图片", notes="编辑图片", response = String.class)
//	@PostMapping("/edit")
//	public String edit(HttpServletRequest request,
//			@ApiParam(name = "uid", value = "唯一UID",required = true) @RequestParam(name = "uid", required = true) String uid,
//			@ApiParam(name = "fileUid", value = "图片UID",required = false) @RequestParam(name = "fileUid", required = false) String fileUid,
//			@ApiParam(name = "picName", value = "图片名称",required = false) @RequestParam(name = "picName", required = false) String picName,
//			@ApiParam(name = "pictureSortUid", value = "图片分类UID",required = false) @RequestParam(name = "pictureSortUid", required = false) String pictureSortUid) {
//
//		if(StringUtils.isEmpty(uid)) {
//			return ResultUtil.result(SysConf.ERROR, "数据错误");
//		}
//		Picture picture = pictureService.getById(uid);
//		picture.setFileUid(fileUid);
//		picture.setPicName(picName);
//		picture.setPictureSortUid(pictureSortUid);
//		picture.updateById();
//		return ResultUtil.result(SysConf.SUCCESS, "编辑成功");
//	}
//
//	@ApiOperation(value="删除图片", notes="删除图片", response = String.class)
//	@PostMapping("/delete")
//	public String delete(HttpServletRequest request,
//			@ApiParam(name = "uid", value = "唯一UID",required = true) @RequestParam(name = "uid", required = true) String uid			) {
//
//		if(StringUtils.isEmpty(uid)) {
//			return ResultUtil.result(SysConf.ERROR, "数据错误");
//		}
//		Picture picture = pictureService.getById(uid);
//		picture.setStatus(EStatus.DISABLED);
//		picture.updateById();
//		return ResultUtil.result(SysConf.SUCCESS, "删除成功");
//	}
//}
//
