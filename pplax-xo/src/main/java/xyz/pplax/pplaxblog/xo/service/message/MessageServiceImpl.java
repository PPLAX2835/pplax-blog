package xyz.pplax.pplaxblog.xo.service.message;

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
import xyz.pplax.pplaxblog.xo.constants.sql.MessageSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.MessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.MessageMapper;
import xyz.pplax.pplaxblog.xo.service.chatroom.ChatRoomService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

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
    public IPage<Message> list(MessageGetListDto messageGetListDto) {
        QueryWrapper<Message> chatMessageQueryWrapper = new QueryWrapper<>();
        chatMessageQueryWrapper.ne(MessageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        if (!StringUtils.isEmpty(messageGetListDto.getKeyword())) {
            chatMessageQueryWrapper.like(MessageSQLConstants.CONTENT, "%" + messageGetListDto.getKeyword() + "%");
        }
        if (messageGetListDto.getType() != null) {
            chatMessageQueryWrapper.eq(MessageSQLConstants.TYPE, messageGetListDto.getType());
        }
        if (!StringUtils.isEmpty(messageGetListDto.getChatRoomUid())) {
            chatMessageQueryWrapper.eq(MessageSQLConstants.CHAT_ROOM_UID, messageGetListDto.getChatRoomUid());
        }

        //分页
        Page<Message> page = new Page<>();
        page.setCurrent(messageGetListDto.getCurrentPage());
        page.setSize(messageGetListDto.getPageSize());

        IPage<Message> pageList = page(page, chatMessageQueryWrapper);
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
    public IPage<Message> listChatMessage(String userUid, String chatRoomUid, Long currentPage, Long pageSize) {
        QueryWrapper<Message> chatMessageQueryWrapper = new QueryWrapper<>();
        chatMessageQueryWrapper.ne(MessageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        chatMessageQueryWrapper.eq(MessageSQLConstants.TYPE, CharacterConstants.NUM_ONE);
        if (!StringUtils.isEmpty(chatRoomUid)) {
            chatMessageQueryWrapper.eq(MessageSQLConstants.CHAT_ROOM_UID, chatRoomUid);
        }
        chatMessageQueryWrapper.orderByDesc(MessageSQLConstants.CREATE_TIME);

        //分页
        Page<Message> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        IPage<Message> pageList = page(page, chatMessageQueryWrapper);
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
    public IPage<Message> listLeaveMessage(Long currentPage, Long pageSize) {
        QueryWrapper<Message> chatMessageQueryWrapper = new QueryWrapper<>();
        chatMessageQueryWrapper.ne(MessageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        chatMessageQueryWrapper.eq(MessageSQLConstants.TYPE, CharacterConstants.NUM_ZERO);
        chatMessageQueryWrapper.orderByDesc(MessageSQLConstants.CREATE_TIME);

        //分页
        Page<Message> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        IPage<Message> pageList = page(page, chatMessageQueryWrapper);
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
