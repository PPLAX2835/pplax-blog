package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.ChatRoomEditDto;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;
import xyz.pplax.pplaxblog.xo.entity.User;

import java.util.List;

/**
 * 聊天室 服务类
 */
public interface ChatRoomService extends SuperService<ChatRoom> {

    Page<ChatRoom> page(Long currentPage, Long pageSize);

    List<ChatRoom> listByUserUid(String userUid, Boolean isOwner);

    List<User> listChatRoomMember(String chatRoomUid);

    Page<ChatRoom> pageGroupChatNotInByName(String userUid, String name, Long currentPage, Long pageSize);

    Boolean exitChatRoom(String userUid, String chatRoomUid);

    Boolean kickChatRoomMember(String userUid, String chatRoomUid, String memberUid);

    Boolean joinChatRoom(String userUid, String chatRoomUid);

    Boolean createChatRoom(String userUid, String name, String avatarUid, Integer type, String memberUids);

}
