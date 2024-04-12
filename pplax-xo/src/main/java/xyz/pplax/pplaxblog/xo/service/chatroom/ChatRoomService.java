package xyz.pplax.pplaxblog.xo.service.chatroom;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.ChatRoomGetListDto;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;

import java.util.List;

/**
 * 聊天室 服务类
 */
public interface ChatRoomService extends SuperService<ChatRoom> {

    IPage<ChatRoom> list(ChatRoomGetListDto chatRoomGetListDto);

//    List<ChatRoom> getByUserUid(String userUid);
}
