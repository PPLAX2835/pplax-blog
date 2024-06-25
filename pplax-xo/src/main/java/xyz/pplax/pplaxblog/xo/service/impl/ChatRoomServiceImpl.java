package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.ChatRoomSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.ChatRoomMapper;
import xyz.pplax.pplaxblog.xo.service.ChatRoomService;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 菜单 服务实现类
 */
@Service
public class ChatRoomServiceImpl extends SuperServiceImpl<ChatRoomMapper, ChatRoom> implements ChatRoomService {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public Page<ChatRoom> page(Long currentPage, Long pageSize) {
        PQueryWrapper<ChatRoom> chatRoomPQueryWrapper = new PQueryWrapper<>();

        //分页
        Page<ChatRoom> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<ChatRoom> pageList = page(page, chatRoomPQueryWrapper);
        for (ChatRoom chatRoom : pageList.getRecords()) {
            // 封装头像
            chatRoom.setAvatar(fileStorageService.getById(chatRoom.getAvatarUid()));

            // 封装群主
            if (chatRoom.getType() == CharacterConstants.NUM_ZERO || chatRoom.getType() == CharacterConstants.NUM_ONE) {
                User user = userService.getById(chatRoom.getOwnerUid());
                if (user != null) {
                    user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                }
                chatRoom.setOwner(user);
            }
        }

        return pageList;
    }

    /**
     * 退出聊天室，如果是群主就直接解散
     * @param userUid
     * @param chatRoomUid
     * @return
     */
    @Override
    public Boolean exitChatRoom(String userUid, String chatRoomUid) {
        ChatRoom chatRoom = getById(chatRoomUid);

        // 如果是群主就直接解散
        if (chatRoom.getOwnerUid().equals(userUid)) {
            return removeById(chatRoomUid);
        }

        // 创建一个列表来存放保留的UID
        String[] uidArray = chatRoom.getMemberUids().split(",");

        // 使用StringBuilder来构建新的字符串
        StringBuilder result = new StringBuilder();

        for (String uid : uidArray) {
            // 如果当前uid不是要移除的那个，才追加到结果中
            if (!uid.equals(userUid)) {
                if (result.length() > 0) {
                    result.append(",");
                }
                result.append(uid);
            }
        }
        chatRoom.setMemberUids(result.toString());

        return updateById(chatRoom);
    }

    /**
     * 加入群聊
     * @param userUid
     * @param chatRoomUid
     * @return
     */
    @Override
    public Boolean joinChatRoom(String userUid, String chatRoomUid) {
        ChatRoom chatRoom = getById(chatRoomUid);

        if (chatRoom == null || chatRoom.getOwnerUid().equals(userUid) || chatRoom.getMemberUids().contains(userUid)) {
            return false;
        }

        String newMemberUids = chatRoom.getMemberUids() + "," + userUid;
        newMemberUids = StringUtils.removeStart(newMemberUids, ",");
        newMemberUids = StringUtils.removeEnd(newMemberUids, ",");

        if (newMemberUids.split(",").length > 50) {
            return false;
        }

        chatRoom.setMemberUids(newMemberUids);

        return updateById(chatRoom);
    }

    @Override
    public Page<ChatRoom> pageGroupChatNotInByName(String userUid, String name, Long currentPage, Long pageSize) {
        PQueryWrapper<ChatRoom> chatRoomPQueryWrapper = new PQueryWrapper<>();

        // 排除掉已经进入的群聊
        chatRoomPQueryWrapper
                .and(
                        QueryWrapper -> QueryWrapper
                                .ne(ChatRoomSQLConstants.OWNER_UID, userUid)
                                .notLike(ChatRoomSQLConstants.MEMBER_UIDS, "%" + userUid + "%")
                );

        // 只要群聊类型
        chatRoomPQueryWrapper.eq(ChatRoomSQLConstants.TYPE, CharacterConstants.NUM_ONE);

        // 根据群聊名查询
        chatRoomPQueryWrapper.like(ChatRoomSQLConstants.NAME, "%" + name + "%");

        //分页
        Page<ChatRoom> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<ChatRoom> pageList = page(page, chatRoomPQueryWrapper);

        for (ChatRoom chatRoom : pageList.getRecords()) {
            // 封装头像
            chatRoom.setAvatar(fileStorageService.getById(chatRoom.getAvatarUid()));
        }

        return pageList;
    }

    @Override
    public List<ChatRoom> listByUserUid(String userUid, Boolean isOwner) {
        PQueryWrapper<ChatRoom> chatRoomPQueryWrapper = new PQueryWrapper<>();

        if (isOwner) {
            // 如果只查询自己是群主的
            chatRoomPQueryWrapper.eq(ChatRoomSQLConstants.OWNER_UID, userUid);
        } else {
            // 查询所有自己所在的群
            chatRoomPQueryWrapper
                    .and(
                            QueryWrapper -> QueryWrapper
                                    .eq(ChatRoomSQLConstants.OWNER_UID, userUid)
                                    .or()
                                    .like(ChatRoomSQLConstants.MEMBER_UIDS, "%" + userUid + "%")
                    );
        }

        List<ChatRoom> chatRoomList = list(chatRoomPQueryWrapper);

        for (ChatRoom chatRoom : chatRoomList) {
            // 封装头像
            chatRoom.setAvatar(fileStorageService.getById(chatRoom.getAvatarUid()));
        }

        return chatRoomList;
    }
}
