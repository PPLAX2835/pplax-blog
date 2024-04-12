package xyz.pplax.pplaxblog.xo.service.message;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.MessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Message;

/**
 * 聊天记录 服务类
 */
public interface MessageService extends SuperService<Message> {

    IPage<Message> list(MessageGetListDto messageGetListDto);

}
