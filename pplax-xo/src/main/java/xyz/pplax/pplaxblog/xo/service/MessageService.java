package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.Message;

/**
 * 聊天记录 服务类
 */
public interface MessageService extends SuperService<Message> {

    Page<Message> page(String keyword, Integer type, String chatRoomUid, Long currentPage, Long pageSize);

    Page<Message> pageChatMessage(String userUid, String chatRoomUid, Long currentPage, Long pageSize);

    Page<Message> pageLeaveMessage(Long currentPage, Long pageSize);

    Boolean read(String userUid, String chatRoomUid);
}
