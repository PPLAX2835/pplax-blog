package xyz.pplax.article.magager.amqp.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.pplax.amqp.comstant.AmqpQueueNameConstant;
import xyz.pplax.amqp.config.service.MistakeMessageSendService;
import xyz.pplax.article.pojo.ArticlePojo;
import xyz.pplax.article.pojo.TalkPojo;
import xyz.pplax.article.service.ArticleService;
import xyz.pplax.article.service.TalkService;
import xyz.pplax.article.vo.ArticleVO;
import xyz.pplax.article.vo.TalkVO;
import xyz.pplax.comment.pojo.CommentPojo;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.feign.config.service.MessageLogFeignService;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 消费从评论服务发送的评论消息，修改说说或者是文章的commentUidS值，根据PageUid的值，进行选择
 */
@Component
public class ReceiveComment {

    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TalkService talkService;
    @Autowired
    private MessageLogFeignService.UpdateMessageLog updateMessageLog;

    /**
     * 当某篇文章收到评论时，会向mq中发送消息，文章服务更新该篇文章的评论信息
     * @param msgJson
     * @param channel
     * @param message
     * @throws IOException
     * @throws BindException
     */
    @RabbitListener(queues = AmqpQueueNameConstant.PAGE_COMMENT_QUEUE,ackMode = "AUTO")
    private void receiveCommentConsumer(String msgJson, Channel channel, Message message) throws IOException, BindException {
        CommentPojo comment = parseComment(msgJson, channel, message);
        if (comment == null || comment.getPageUid() == null) {
            return;
        }

        Long pageUid = comment.getPageUid();
        // 根据此pageUid查询article或者talk，看看是哪个的uid
        ArticleVO articleVO = articleService.queryArticleByUid(pageUid);
        if (articleVO != null) {
            updateArticleData(articleVO, comment);
            ack(channel, message);
            return;
        }

        // 查询是否是说说的评论
        TalkVO talkVO = talkService.queryTalkByUid(pageUid);
        if (talkVO != null) {
            // 是文章的评论uid
            updateTalkData(talkVO, comment);
            ack(channel, message);
            return;
        }

        // 运行到这里，说名此评论并不是说说和文章的评论，因为暂时没有加入其他的评论页面，所以也当错误消息处理
        mistakeMessageSendService.sendMistakeMessageToExchange(msgJson, channel, message);
        updateMessageLog.updateMessageLogInfo(comment.getUid() + "",
                true, true, "此评论没有页面可以消费", message);
    }

    /**
     * 从mq发送的消息中，解析出需要评论数据
     * @param json
     * @param channel
     * @param message
     * @return
     * @throws IOException
     */
    private CommentPojo parseComment(String json, Channel channel, Message message) throws IOException {
        CommentPojo commentPojo = null;
        try {
            commentPojo = JSON.parseObject(json, CommentPojo.class);
        } catch (Exception e) {
            mistakeMessageSendService.sendMistakeMessageToExchange(json,channel,message);
            return null;
        }
        return commentPojo;
    }

    /**
     * 设置文章评论的uid
     * @param commentUids
     * @param uid
     * @return
     */
    private String setCommentUids(String commentUids, Long uid) {
        if (!StringUtils.hasLength(commentUids)) {
            return uid + "";
        }
        return Stream.concat(Stream.of(uid + ""), Arrays.stream(commentUids.split(",")))
                .distinct()
                .collect(Collectors.joining(","));
    }

    private void updateArticleData(ArticleVO articleVO, CommentPojo comment) {
        // 是文章的评论uid
        articleVO.setCommentUids(setCommentUids(articleVO.getCommentUids(), comment.getUid()));
        articleService.updateArticle(BeanUtils.copyProperties(articleVO, ArticlePojo.class));
    }

    private void updateTalkData(TalkVO talkVO, CommentPojo comment) {
        // 是文章的评论uid
        talkVO.setCommentUids(setCommentUids(talkVO.getCommentUids(), comment.getUid()));
        talkService.updateTalk(BeanUtils.copyProperties(talkVO, TalkPojo.class));
    }

    private void ack(Channel channel, Message message) throws IOException, BindException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        updateMessageLog.updateMessageLogInfo(message.getMessageProperties().getCorrelationId(), true, true, null, message);
    }
}
