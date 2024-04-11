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
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

/**
 * 菜单 服务实现类
 */
@Service
public class ChatRoomServiceImpl extends SuperServiceImpl<ChatRoomMapper, ChatRoom> implements ChatRoomService {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;


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
            if (chatRoom.getType() == CharacterConstants.NUM_ONE) {
                // 封装用户信息
                User user = userService.getById(chatRoom.getMember1Uid());
                if (user != null) {
                    user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                    user.sensitiveDataRemove();
                    chatRoom.setMember1(user);
                }

                user = userService.getById(chatRoom.getMember2Uid());
                if (user != null) {
                    user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                    user.sensitiveDataRemove();
                    chatRoom.setMember2(user);
                }
            }
        }

        return pageList;
    }
}
