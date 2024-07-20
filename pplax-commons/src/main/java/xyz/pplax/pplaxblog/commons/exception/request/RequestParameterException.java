package xyz.pplax.pplaxblog.commons.exception.request;


import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

public class RequestParameterException extends RequestException {

    public RequestParameterException() {
        super();
    }

    public RequestParameterException(String msg) {
        super(msg);
    }

    public RequestParameterException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
