package xyz.pplax.pplaxblog.message.consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.service.CommentService;

/**
 * @ClassName: CommentConsumer
 * @Author: PPLAX
 * @Date: 2024/4/22
 **/
@Component
public class CommentConsumer {

    private static Logger log = LogManager.getLogger(CommentConsumer.class);

    @Autowired
    private CommentService commentService;

    @RabbitListener(queues = MqConstants.PPLAX_COMMENT)
    public void saveMessage(Comment comment) {
        log.info("接收到需要保存的评论消息：" + comment);
        commentService.save(comment);
    }


}
