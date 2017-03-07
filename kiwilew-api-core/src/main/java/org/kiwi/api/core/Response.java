package org.kiwi.api.core;

import java.io.Serializable;

/**
 * Description: API Controller返回结果<br/>
 *
 * @author kiwilew
 * @date: 2017年3月3日 下午7:30:16
 * @version 1.0
 * @since JDK 1.7
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 6619992106814874490L;

    /** 返回状态码 **/
    private String code;

    /** 返回信息，code200时为空 **/
    private String msg;

    /** 返回结果数据 **/
    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response [code=" + code + ", msg=" + msg + ", result=" + result + "]";
    }



    public enum Code {

        succ("200", "成功"),

        auth("400", "未授权的访问"),

        not_exist("404", "服务不存在"),

        error("500", "服务错误");

        private String code;

        private String desc;

        Code(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

    }

}
