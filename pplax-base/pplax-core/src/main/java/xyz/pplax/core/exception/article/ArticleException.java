package xyz.pplax.core.exception.article;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 文章相关异常
 */
public class ArticleException extends PPLAXException {

    public ArticleException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public ArticleException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public ArticleException(String message) {
        super(message);
    }
}
