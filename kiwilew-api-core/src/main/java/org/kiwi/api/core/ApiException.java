package org.kiwi.api.core;

/**
 * Description: API异常<br/>
 *
 * @author kiwilew
 * @date: 2017年3月3日 下午8:21:25
 * @version 1.0
 * @since JDK 1.7
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -1698548146880733640L;

    public static final String UNKNOW = "500";

    public static final String ILLEGAL_ARGUMENT = "403";

    /** 异常码 */
    private String code;

    /** 异常消息 */
    private String msg;

    public ApiException(Throwable cause) {
        super(cause);
        if (cause instanceof ApiException) {
            this.code = ((ApiException) cause).getCode();
            this.msg = ((ApiException) cause).getMsg();
        } else if (cause instanceof IllegalArgumentException) {
            this.code = ILLEGAL_ARGUMENT;
            this.msg = cause.getMessage();
        } else {
            this.code = UNKNOW;
            if (cause != null) {
                this.msg = cause.getMessage();
            }
        }
    }

    public ApiException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ApiException(String code, String msg, Throwable cause) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.initCause(cause);
    }

    public String toString() {
        String s = getClass().getName();
        StringBuilder e = new StringBuilder();
        e.append(s);
        if (code != null) {
            e.append(":[").append(code).append("]");
        }
        if (msg != null) {
            e.append(" ").append(msg);
        }
        return e.toString();
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
