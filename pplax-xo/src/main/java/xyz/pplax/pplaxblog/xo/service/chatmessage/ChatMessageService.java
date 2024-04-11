package xyz.pplax.pplaxblog.xo.service.chatmessage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.ChatMessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.ChatMessage;

/**
 * 聊天记录 服务类
 */
public interface ChatMessageService extends SuperService<ChatMessage> {

    IPage<ChatMessage> list(ChatMessageGetListDto chatMessageGetListDto);

}
