package xyz.pplax.pplaxblog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xyz.pplax.pplaxblog.xo.entity.Admin;
import xyz.pplax.pplaxblog.xo.service.AdminService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	private static Logger log = LogManager.getLogger(AdminController.class);
	
	@GetMapping("/getList")
	public String getList() {
		log.info("生成getList");
		List<Admin> list = adminService.list(new QueryWrapper<Admin>().eq("username", "admin"));		
		return list.toString();
	}
}

