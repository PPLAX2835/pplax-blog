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
import xyz.pplax.pplaxblog.xo.dto.edit.ChatRoomEditDto;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
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

    @Override
    public Boolean kickChatRoomMember(String userUid, String chatRoomUid, String memberUid) {
        ChatRoom chatRoom = getById(chatRoomUid);

        // 如果不是群主就直接返回
        if (!chatRoom.getOwnerUid().equals(userUid)) {
            return false;
        }

        // 创建一个列表来存放保留的UID
        String[] uidArray = chatRoom.getMemberUids().split(",");

        // 使用StringBuilder来构建新的字符串
        StringBuilder result = new StringBuilder();

        for (String uid : uidArray) {
            // 如果当前uid不是要移除的那个，才追加到结果中
            if (!uid.equals(memberUid)) {
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
     * 退出聊天室，如果是群主就直接解散
     * @param userUid
     * @param chatRoomUid
     * @return
     */
    @Override
    public Boolean exitChatRoom(String userUid, String chatRoomUid) {
        ChatRoom chatRoom = getById(chatRoomUid);

        // 如果是自己群主就直接解散，如果是私信就直接删除
        if (chatRoom.getType() == 2 || chatRoom.getOwnerUid().equals(userUid)) {
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

    /**
     * 创建聊天室
     * @param userUid
     * @param name
     * @param avatarUid
     * @param type
     * @param memberUids
     * @return
     */
    @Override
    public Boolean createChatRoom(String userUid, String name, String avatarUid, Integer type, String memberUids) {
        if (type == null || (type != 1 && type != 2)) {
            type = 1;
        }
        ChatRoom chatRoom = new ChatRoom();
        if (type == 1) {
            chatRoom.setOwnerUid(userUid);
            chatRoom.setMemberUids(userUid);            // 如果是群聊，那么刚开始创建的时候成员只有群主一人
        } else {
            // 检查一下私聊是否已经创建过了
            PQueryWrapper<ChatRoom> chatRoomPQueryWrapper = new PQueryWrapper<>();
            chatRoomPQueryWrapper.eq(ChatRoomSQLConstants.TYPE, 2);

            String[] memberUidArr = memberUids.split(",");
            for (String memberUid : memberUidArr) {
                chatRoomPQueryWrapper.like(ChatRoomSQLConstants.MEMBER_UIDS, "%" + memberUid + "%");
            }

            ChatRoom chatRoom1 = getOne(chatRoomPQueryWrapper);
            if (chatRoom1 != null) {
                // 晚点优化一下这里
                return false;
            }

            chatRoom.setMemberUids(memberUids);        // 私聊只有两个人
        }
        chatRoom.setName(name);
        chatRoom.setAvatarUid(avatarUid);
        chatRoom.setType(type);

        return save(chatRoom);
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
            if (chatRoom.getType() == 2) {
                // 私聊对象需要处理一下
                String privateChatUserUid = chatRoom.getMemberUids().replace(userUid, "").replace(",", "");
                UserInfo privateChatUserInfo = userInfoService.getByUserUid(privateChatUserUid);
                if (privateChatUserInfo != null) {
                    chatRoom.setAvatar(fileStorageService.getById(privateChatUserInfo.getAvatarPictureUid()));
                    chatRoom.setName(privateChatUserInfo.getNickname());
                }
            } else {
                // 封装头像
                chatRoom.setAvatar(fileStorageService.getById(chatRoom.getAvatarUid()));
            }
        }

        return chatRoomList;
    }

    /**
     * 获取群成员列表
     * @param chatRoomUid
     * @return
     */
    @Override
    public List<User> listChatRoomMember(String chatRoomUid) {
        ChatRoom chatRoom = getById(chatRoomUid);

        if (chatRoom != null && !StringUtils.isEmpty(chatRoom.getMemberUids())) {
            String[] memberUids = chatRoom.getMemberUids().split(",");
            List<User> memberList = userService.listByIds(Arrays.asList(memberUids));

            // 封装用户信息
            for (User user : memberList) {
                user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                user.sensitiveDataRemove();
            }

            return memberList;
        }

        return null;
    }
}
