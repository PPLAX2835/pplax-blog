package xyz.pplax.pplaxblog.xo.service.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.MessageSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.MessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.mapper.MessageMapper;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

/**
 * 聊天记录 服务实现类
 */
@Service
public class MessageServiceImpl extends SuperServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private UserInfoService userInfoService;

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

        //分页
        Page<Message> page = new Page<>();
        page.setCurrent(messageGetListDto.getCurrentPage());
        page.setSize(messageGetListDto.getPageSize());

        IPage<Message> pageList = page(page, chatMessageQueryWrapper);
        for (Message chatMessage : pageList.getRecords()) {
            // 封装用户信息
            chatMessage.setUserInfo(userInfoService.getByUserUid(chatMessage.getUserUid()));
        }

        return pageList;
    }
}
