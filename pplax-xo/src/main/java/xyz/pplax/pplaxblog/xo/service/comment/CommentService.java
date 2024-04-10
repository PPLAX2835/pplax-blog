package xyz.pplax.pplaxblog.xo.service.comment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.CommentGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Comment;

import java.util.List;

/**
 * 评论表 服务类
 */
public interface CommentService extends SuperService<Comment> {

    IPage<Comment> list(CommentGetListDto commentGetListDto);

    List<Comment> listByOriginalUid(String originalUid, Integer type);

    IPage<Comment> pageByBlogUid(String blogUid, Integer type, Long currentPage, Long pageSize);
}
