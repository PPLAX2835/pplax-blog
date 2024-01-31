package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.mapper.CommentMapper;
import xyz.pplax.pplaxblog.xo.service.CommentService;

/**
 * 评论表 服务实现类
 */
@Service
public class CommentServiceImpl extends SuperServiceImpl<CommentMapper, Comment> implements CommentService {

}
