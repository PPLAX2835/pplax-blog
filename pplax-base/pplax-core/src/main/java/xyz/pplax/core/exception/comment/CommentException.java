package xyz.pplax.core.exception.comment;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 评论相关异常
 */
public class CommentException extends PPLAXException {

    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public CommentException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }
}