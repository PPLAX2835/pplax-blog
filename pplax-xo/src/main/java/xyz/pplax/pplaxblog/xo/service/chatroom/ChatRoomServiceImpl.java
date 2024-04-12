package xyz.pplax.pplaxblog.xo.service.chatroom;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.ChatRoomSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.LeaveMessageSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.ChatRoomGetListDto;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;
import xyz.pplax.pplaxblog.xo.entity.LeaveMessage;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.ChatRoomMapper;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

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
    public IPage<ChatRoom> list(ChatRoomGetListDto chatRoomGetListDto) {
        QueryWrapper<ChatRoom> chatRoomQueryWrapper = new QueryWrapper<>();
        chatRoomQueryWrapper.ne(ChatRoomSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        //分页
        Page<ChatRoom> page = new Page<>();
        page.setCurrent(chatRoomGetListDto.getCurrentPage());
        page.setSize(chatRoomGetListDto.getPageSize());

        IPage<ChatRoom> pageList = page(page, chatRoomQueryWrapper);
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

//    @Override
//    public List<ChatRoom> getByUserUid(String userUid) {
//        QueryWrapper<ChatRoom> chatRoomQueryWrapper = new QueryWrapper<>();
//        chatRoomQueryWrapper.ne(ChatRoomSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
//
//        chatRoomQueryWrapper.eq(ChatRoomSQLConstants.TYPE, CharacterConstants.NUM_ONE);
//        chatRoomQueryWrapper.
//                eq(ChatRoomSQLConstants.MEMBER1_UID, userUid)
//                .or()
//                .eq(ChatRoomSQLConstants.MEMBER2_UID, userUid);
//
//        List<ChatRoom> chatRoomList = list(chatRoomQueryWrapper);
//
//        chatRoomQueryWrapper = new QueryWrapper<>();
//        chatRoomQueryWrapper.ne(ChatRoomSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
//        chatRoomQueryWrapper.eq(ChatRoomSQLConstants.TYPE, CharacterConstants.NUM_ZERO);
//        chatRoomList.addAll(list(chatRoomQueryWrapper));
//
//        // 封装用户
//        User user1 = userService.getById(userUid);
//        if (user1 != null) {
//            user1.setUserInfo(userInfoService.getById(user1.getUserInfoUid()));
//        }
//
//        for (ChatRoom chatRoom : chatRoomList) {
//            User user2 = null;
//            if (chatRoom.getType() == CharacterConstants.NUM_ONE) {
//                if (!userUid.equals(chatRoom.getMember1Uid())) {
//                    user2 = userService.getById(chatRoom.getMember1Uid());
//                } else {
//                    user2 = userService.getById(chatRoom.getMember2Uid());
//                }
//                if (user2 != null) {
//                    user2.setUserInfo(userInfoService.getById(user2.getUserInfoUid()));
//                }
//            }
//
//            chatRoom.setMember1(user1);
//            chatRoom.setMember2(user2);
//        }
//
//
//
//        return chatRoomList;
//    }
}
