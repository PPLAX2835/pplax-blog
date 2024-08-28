package xyz.pplax.pplaxblog.message.consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.MenuSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.type.MenuTypeConstants;
import xyz.pplax.pplaxblog.xo.entity.ExceptionLog;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.service.ExceptionLogService;
import xyz.pplax.pplaxblog.xo.service.MenuService;
import xyz.pplax.pplaxblog.xo.service.RequestLogService;


/**
 * @ClassName: ExceptionLogConsumer
 * @Author: PPLAX
 * @Date: 2024/8/28
 **/
@Component
public class ExceptionLogConsumer {

    private static Logger log = LogManager.getLogger(ExceptionLogConsumer.class);

    @Autowired
    private ExceptionLogService exceptionLogService;

    @Autowired
    private MenuService menuService;

    @RabbitListener(queues = MqConstants.PPLAX_EXCEPTION_LOG)
    public void saveMessage(ExceptionLog exceptionLog) {
        // 创建查询
        PQueryWrapper<Menu> menuPQueryWrapper = new PQueryWrapper<>();
        menuPQueryWrapper.isNotNull(MenuSQLConstants.ENDPOINT)
                .eq(MenuSQLConstants.TYPE, MenuTypeConstants.BUTTON)
                .ne(MenuSQLConstants.ENDPOINT, "")
                .apply("'" + exceptionLog.getEndpoint() + "' LIKE REPLACE(endpoint, '*', '%')")
                .orderByDesc("LENGTH(endpoint)")
                .last("limit 1");
        // 查询
        Menu menu = menuService.getOne(menuPQueryWrapper);

        // 封装apiId
        if (menu != null) {
            exceptionLog.setMenuUid(menu.getUid());
        }

        // 持久化
        exceptionLogService.save(exceptionLog);
    }

}
