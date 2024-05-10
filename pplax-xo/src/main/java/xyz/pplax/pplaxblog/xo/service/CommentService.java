package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.CommentGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Comment;

import java.util.List;

/**
 * 评论表 服务类
 */
public interface CommentService extends SuperService<Comment> {

    Page<Comment> page(String keyword, String nickname, Integer type, String originalUid, Long currentPage, Long pageSize);

    List<Comment> listByOriginalUid(String originalUid, Integer type);

    Page<Comment> pageByOriginalUid(String originalUid, Integer type, Long currentPage, Long pageSize);

    Page<Comment> pageByBlogUid(String blogUid, Integer type, Long currentPage, Long pageSize);

    Boolean like(String originalUid, String userUid, Integer type);
}
