package xyz.pplax.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.comment.po.Comment;

/**
 * comment数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "comment数据表的VO")
public class CommentVO extends Comment {

}