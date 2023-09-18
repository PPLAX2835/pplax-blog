package xyz.pplax.comment.manager.amqp.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import xyz.pplax.amqp.api.AmqpSenderService;
import xyz.pplax.amqp.comstant.AmqpExchangeNameConstant;
import xyz.pplax.amqp.comstant.AmqpQueueNameConstant;
import xyz.pplax.comment.po.Comment;
import xyz.pplax.comment.pojo.CommentPojo;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.ConvertObjectUtils;

/**
 * 将新评论发送到pplax.send.comment.exchange交换机中，文章服务消费
 */
@Component
public class SendCommentToExchange {

    @Autowired
    private AmqpSenderService amqpSenderService;

    @Transactional
    public void sendCommentToMQ(Comment comment, Long pageUid) throws BindException {
        CommentPojo commentPojo = BeanUtils.copyProperties(comment, CommentPojo.class);
        commentPojo.setPageUid(pageUid);
        String json = ConvertObjectUtils.jsonToString(commentPojo);
        amqpSenderService.sendMQMsg(json, AmqpExchangeNameConstant.PPLAX_SEND_COMMENT_EXCHANGE,
                AmqpQueueNameConstant.PAGE_COMMENT_ROUTING_KEY, "topic");
    }
}
