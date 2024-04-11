package xyz.pplax.pplaxblog.xo.service.leavemessage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.LeaveMessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.LeaveMessage;

/**
 * 留言 服务类
 */
public interface LeaveMessageService extends SuperService<LeaveMessage> {

    IPage<LeaveMessage> list(LeaveMessageGetListDto leaveMessageGetListDto);

}
