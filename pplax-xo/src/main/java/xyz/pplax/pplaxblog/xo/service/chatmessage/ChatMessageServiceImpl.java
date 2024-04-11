package xyz.pplax.pplaxblog.xo.service.chatmessage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.ChatMessageSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.ChatMessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.ChatMessage;
import xyz.pplax.pplaxblog.xo.mapper.ChatMessageMapper;
import xyz.pplax.pplaxblog.xo.service.chatroom.ChatRoomService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

/**
 * 聊天记录 服务实现类
 */
@Service
public class ChatMessageServiceImpl extends SuperServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public IPage<ChatMessage> list(ChatMessageGetListDto chatMessageGetListDto) {
        QueryWrapper<ChatMessage> chatMessageQueryWrapper = new QueryWrapper<>();
        chatMessageQueryWrapper.ne(ChatMessageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        if (!StringUtils.isEmpty(chatMessageGetListDto.getKeyword())) {
            chatMessageQueryWrapper.like(ChatMessageSQLConstants.CONTENT, "%" + chatMessageGetListDto.getKeyword() + "%");
        }

        //分页
        Page<ChatMessage> page = new Page<>();
        page.setCurrent(chatMessageGetListDto.getCurrentPage());
        page.setSize(chatMessageGetListDto.getPageSize());

        IPage<ChatMessage> pageList = page(page, chatMessageQueryWrapper);
        for (ChatMessage chatMessage : pageList.getRecords()) {
            // 封装用户信息
            chatMessage.setUserInfo(userInfoService.getByUserUid(chatMessage.getUserUid()));
        }

        return pageList;
    }
}
