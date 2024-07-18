package xyz.pplax.pplaxblog.message.component;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.constants.redis.ChatRedisConstants;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;
import xyz.pplax.pplaxblog.xo.service.UserService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: pplax
 * @Date: 2024-06-26 17:59
 * @Description:
 */
@Component
@ServerEndpoint(value = "/chat/room/{chatRoomUid}/member/{userUid}")
public class ChatWebSocket {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    private static RedisService redisService;

    private static UserService userService;

    private static UserInfoService userInfoService;

    /**
     * 用来记录所有的session
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap(8);

    /**
     * 采用这种方式注入是因为webSocket由tomcat托管什么的
     * @param redisService
     */
    @Autowired
    public void setRedisServiceImpl(RedisService redisService){
        ChatWebSocket.redisService = redisService;
    }

    @Autowired
    private void setUserServiceImpl(UserService userService) {
        ChatWebSocket.userService = userService;
    }

    @Autowired
    private void setUserInfoServiceImpl(UserInfoService userInfoService) {
        ChatWebSocket.userInfoService = userInfoService;
    }

    @OnOpen
    public void open( @PathParam("chatRoomUid") String chatRoomUid, Session session, @PathParam("userUid") String userUid ) {
        // 先从缓存中获取map  Map<chatRoomUid, Map<userUid, sessionId>>
        Map<String, Map<String, String>> roomMap = redisService.getCacheMap(ChatRedisConstants.ONLINE_CHAT_ROOM + ChatRedisConstants.SEGMENTATION + chatRoomUid);
        if (roomMap == null) {
            roomMap = new HashMap<>();
        }

        // 记录session到sessionMap中
        sessionMap.put(session.getId(), session);

        // 获取这个聊天室中的userMap 是 userUid和session的对应关系
        Map<String, String> userUidSessionMap = roomMap.get(chatRoomUid);

        // 如果是新的房间，则创建一个映射，如果房间已存在，则把用户放进去
        if (userUidSessionMap == null) {
            userUidSessionMap = new HashMap<>();
            userUidSessionMap.put(userUid, session.getId());
            roomMap.put(chatRoomUid, userUidSessionMap);
        } else {
            userUidSessionMap.put(userUid, session.getId());
        }

        // 记录到缓存中
        redisService.setCacheMap(ChatRedisConstants.ONLINE_CHAT_ROOM + ChatRedisConstants.SEGMENTATION + chatRoomUid, roomMap);
    }

    @OnClose
    public void close( @PathParam("chatRoomUid") String chatRoomUid, Session session, @PathParam("userUid") String userUid ) {
        // 先从缓存中获取map  Map<chatRoomUid, Map<userUid, sessionId>>
        Map<String, Map<String, String>> roomMap = redisService.getCacheMap(ChatRedisConstants.ONLINE_CHAT_ROOM + ChatRedisConstants.SEGMENTATION + chatRoomUid);
        if (roomMap == null) {
            roomMap = new HashMap<>();
        }

        // 移除session
        sessionMap.remove(session.getId());

        // 如果用户离开了，就移除相应的信息
        if (roomMap.containsKey(chatRoomUid)) {
            Map<String, String> userUidSessionIdMap = roomMap.get(chatRoomUid);
            userUidSessionIdMap.remove(userUid);
            if (userUidSessionIdMap.size() == 0) {
                // 既然用户都没了，就先把这个聊天室key删掉
                redisService.deleteObject(ChatRedisConstants.ONLINE_CHAT_ROOM + ChatRedisConstants.SEGMENTATION + chatRoomUid);
                return;
            }
        }

        // 记录到缓存中
        redisService.setCacheMap(ChatRedisConstants.ONLINE_CHAT_ROOM + ChatRedisConstants.SEGMENTATION + chatRoomUid, roomMap);
    }

    @OnMessage
    public void receiveMessage( @PathParam("chatRoomUid") String chatRoomUid, Session session, String message , @PathParam("userUid") String userUid ) throws IOException {

        log.info("接受到sessionId为{}，userUid为{}的数据:{}", session.getId(), userUid, message);
        Message messageObj = JSON.toJavaObject(JSON.parseObject(message), Message.class);
        messageObj.setUserUid(userUid);
        messageObj.setChatRoomUid(chatRoomUid);
        User user = userService.getById(userUid);
        if (user != null) {
            messageObj.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
        }


        // 先从缓存中获取map  Map<chatRoomUid, Map<userUid, sessionId>>
        Map<String, Map<String, String>> roomMap = redisService.getCacheMap(ChatRedisConstants.ONLINE_CHAT_ROOM + ChatRedisConstants.SEGMENTATION + chatRoomUid);
        if (roomMap == null) {
            roomMap = new HashMap<>();
        }

        Map<String, String> userUidSessionIdMap = roomMap.get(chatRoomUid);

        // 给房间内所有用户推送信息
        for (String sessionId : userUidSessionIdMap.values()) {
            Session s = sessionMap.get(sessionId);
            if (!s.getId().equals(session.getId())) {
                s.getBasicRemote().sendText(JSON.toJSONString(messageObj));
            }
        }
    }


    @OnError
    public void error( Throwable throwable ) {
        try {
            throw throwable;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
