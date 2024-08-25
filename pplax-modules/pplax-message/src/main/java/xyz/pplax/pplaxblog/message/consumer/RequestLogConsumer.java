package xyz.pplax.pplaxblog.message.consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.MenuSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.type.MenuTypeConstants;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.entity.RequestLog;
import xyz.pplax.pplaxblog.xo.service.MenuService;
import xyz.pplax.pplaxblog.xo.service.RequestLogService;


/**
 * @ClassName: RequestLogConsumer
 * @Author: PPLAX
 * @Date: 2024/8/24
 **/
@Component
public class RequestLogConsumer {

    private static Logger log = LogManager.getLogger(RequestLogConsumer.class);

    @Autowired
    private RequestLogService requestLogService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisService redisService;

    @RabbitListener(queues = MqConstants.PPLAX_REQUEST_LOG)
    public void saveMessage(RequestLog requestLog) {
        // 获得三个携带的数据
        String path = requestLog.getPath();
        String method = requestLog.getMethod();
        String fullPath = method + ":" + path;

        // 创建查询
        PQueryWrapper<Menu> menuPQueryWrapper = new PQueryWrapper<>();
        menuPQueryWrapper.isNotNull(MenuSQLConstants.ENDPOINT)
                .eq(MenuSQLConstants.TYPE, MenuTypeConstants.BUTTON)
                .ne(MenuSQLConstants.ENDPOINT, "")
                .apply("'" + fullPath + "' LIKE REPLACE(endpoint, '*', '%')")
                .orderByDesc("LENGTH(endpoint)")
                .last("limit 1");
        // 查询
        Menu menu = menuService.getOne(menuPQueryWrapper);

        // 封装apiId
        if (menu != null) {
            requestLog.setMenuUid(menu.getUid());
        }

        requestLog.setUid(StringUtils.getUUID());

        // 持久化
        requestLogService.save(requestLog);
    }


}
