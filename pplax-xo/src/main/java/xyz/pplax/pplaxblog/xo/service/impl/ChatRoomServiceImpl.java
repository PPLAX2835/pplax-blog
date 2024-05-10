package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public List<ChatRoom> getByUserUid(String userUid) {
        PQueryWrapper<ChatRoom> chatRoomPQueryWrapper = new PQueryWrapper<>();

        chatRoomPQueryWrapper
                .and(
                        QueryWrapper -> QueryWrapper
                        .eq(ChatRoomSQLConstants.OWNER_UID, userUid)
                        .or()
                        .like(ChatRoomSQLConstants.MEMBER_UIDS, "%" + userUid + "%")
                );

        List<ChatRoom> chatRoomList = list(chatRoomPQueryWrapper);

        for (ChatRoom chatRoom : chatRoomList) {
            // 封装头像
            chatRoom.setAvatar(fileStorageService.getById(chatRoom.getAvatarUid()));
        }

        return chatRoomList;
    }
}
