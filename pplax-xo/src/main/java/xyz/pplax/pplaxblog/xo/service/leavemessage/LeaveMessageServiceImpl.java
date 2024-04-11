package xyz.pplax.pplaxblog.xo.service.leavemessage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.CommentSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.LeaveMessageSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.LeaveMessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.LeaveMessage;
import xyz.pplax.pplaxblog.xo.mapper.LeaveMessageMapper;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

/**
 * 菜单 服务实现类
 */
@Service
public class LeaveMessageServiceImpl extends SuperServiceImpl<LeaveMessageMapper, LeaveMessage> implements LeaveMessageService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public IPage<LeaveMessage> list(LeaveMessageGetListDto leaveMessageGetListDto) {
        QueryWrapper<LeaveMessage> leaveMessageQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(leaveMessageGetListDto.getKeyword())) {
            leaveMessageQueryWrapper.like(LeaveMessageSQLConstants.CONTENT, "%" + leaveMessageGetListDto.getKeyword() + "%");
        }
        leaveMessageQueryWrapper.ne(LeaveMessageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        //分页
        Page<LeaveMessage> page = new Page<>();
        page.setCurrent(leaveMessageGetListDto.getCurrentPage());
        page.setSize(leaveMessageGetListDto.getPageSize());

        IPage<LeaveMessage> pageList = page(page, leaveMessageQueryWrapper);
        for (LeaveMessage leaveMessage : pageList.getRecords()) {
            // 封装用户信息
            leaveMessage.setUserInfo(userInfoService.getByUserUid(leaveMessage.getUserUid()));
        }

        return pageList;
    }
}
