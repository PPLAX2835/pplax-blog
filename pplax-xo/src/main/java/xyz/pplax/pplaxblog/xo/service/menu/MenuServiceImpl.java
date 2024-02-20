package xyz.pplax.pplaxblog.xo.service.menu;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.mapper.MenuMapper;

import java.io.Serializable;

/**
 * 菜单 服务实现类
 */
@Service
public class MenuServiceImpl extends SuperServiceImpl<MenuMapper, Menu> implements MenuService {

}
