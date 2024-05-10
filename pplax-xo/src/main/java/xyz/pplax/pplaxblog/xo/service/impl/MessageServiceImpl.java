package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.MessageSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.MessageMapper;
import xyz.pplax.pplaxblog.xo.service.ChatRoomService;
import xyz.pplax.pplaxblog.xo.service.MessageService;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;

import java.util.Arrays;
import java.util.List;

/**
 * 聊天记录 服务实现类
 */
@Service
public class MessageServiceImpl extends SuperServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Override
    public Page<Message> page(String keyword, Integer type, String chatRoomUid, Long currentPage, Long pageSize) {
        PQueryWrapper<Message> chatMessagePQueryWrapper = new PQueryWrapper<>();

        if (!StringUtils.isEmpty(keyword)) {
            chatMessagePQueryWrapper.like(MessageSQLConstants.CONTENT, "%" + keyword + "%");
        }
        if (type != null) {
            chatMessagePQueryWrapper.eq(MessageSQLConstants.TYPE, type);
        }
        if (!StringUtils.isEmpty(chatRoomUid)) {
            chatMessagePQueryWrapper.eq(MessageSQLConstants.CHAT_ROOM_UID, chatRoomUid);
        }

        //分页
        Page<Message> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Message> pageList = page(page, chatMessagePQueryWrapper);
        for (Message chatMessage : pageList.getRecords()) {
            // 封装用户信息
            chatMessage.setUserInfo(userInfoService.getByUserUid(chatMessage.getUserUid()));

            // 封装已读用户
            if (chatMessage.getType() != 0) {
                // 只有聊天消息才封装
                String[] readUserUids = chatMessage.getReadUserUids().split(",");
                List<User> userList = userService.listByIds(Arrays.asList(readUserUids));
                for (User user : userList) {
                    if (user != null) {
                        user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                        user.sensitiveDataRemove();
                    }
                }
                chatMessage.setReadUserList(userList);
            }
        }

        return pageList;
    }

    /**
     * 获取聊天消息
     * @param chatRoomUid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Message> pageChatMessage(String userUid, String chatRoomUid, Long currentPage, Long pageSize) {
        PQueryWrapper<Message> chatMessagePQueryWrapper = new PQueryWrapper<>();

        chatMessagePQueryWrapper.eq(MessageSQLConstants.TYPE, CharacterConstants.NUM_ONE);
        if (!StringUtils.isEmpty(chatRoomUid)) {
            chatMessagePQueryWrapper.eq(MessageSQLConstants.CHAT_ROOM_UID, chatRoomUid);
        }
        chatMessagePQueryWrapper.orderByDesc(MessageSQLConstants.CREATE_TIME);

        //分页
        Page<Message> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Message> pageList = page(page, chatMessagePQueryWrapper);
        for (Message chatMessage : pageList.getRecords()) {
            // 封装用户信息
            chatMessage.setUserInfo(userInfoService.getByUserUid(chatMessage.getUserUid()));

            if (chatMessage.getType() != 0) {
                // 只有聊天消息才封装

                // 封装已读用户
                String[] readUserUids = chatMessage.getReadUserUids().split(",");
                List<User> userList = userService.listByIds(Arrays.asList(readUserUids));
                for (User user : userList) {
                    if (user != null) {
                        user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                        user.sensitiveDataRemove();
                    }
                }
                chatMessage.setReadUserList(userList);

                // 封装聊天室
                chatMessage.setChatRoom(chatRoomService.getById(chatRoomUid));
            }

            // 修改撤回记录
            if (chatMessage.getStatus() == EStatus.WITHDRAW.getStatus()) {
                chatMessage.setContent("该消息已被撤回");
            }

        }

        return pageList;
    }

    /**
     * 获取留言
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Message> pageLeaveMessage(Long currentPage, Long pageSize) {
        PQueryWrapper<Message> chatMessagePQueryWrapper = new PQueryWrapper<>();
        chatMessagePQueryWrapper.eq(MessageSQLConstants.TYPE, CharacterConstants.NUM_ZERO);
        chatMessagePQueryWrapper.orderByDesc(MessageSQLConstants.CREATE_TIME);

        //分页
        Page<Message> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Message> pageList = page(page, chatMessagePQueryWrapper);
        for (Message chatMessage : pageList.getRecords()) {
            // 封装用户信息
            chatMessage.setUserInfo(userInfoService.getByUserUid(chatMessage.getUserUid()));
        }

        return pageList;
    }

    @Override
    public Boolean read(String userUid, String chatRoomUid) {
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.ne(MessageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        messageUpdateWrapper.eq(MessageSQLConstants.CHAT_ROOM_UID, chatRoomUid);
        messageUpdateWrapper.notLike(MessageSQLConstants.READ_USER_UIDS, "%" + userUid + "%");
        messageUpdateWrapper.setSql("read_user_uids = CONCAT(read_user_uids, '," + userUid + "')");

        return update(messageUpdateWrapper);
    }
}
