package org.kiwi.api.core;

import java.io.Serializable;
import java.util.Arrays;

/** 
 * Description: API Controller请求对象<br/>
 *
 * @author kiwilew
 * @date: 2017年3月3日 下午7:30:16
 * @version 1.0
 * @since JDK 1.7
 */
public class Request implements Serializable {
    
    private static final long serialVersionUID = 4269371216414185304L;

    /** 请求服务方法 **/
    public static final String REQ_DATA_METHOD = "method";
    
    /** 请求用户标识 **/
    public static final String REQ_DATA_UK = "uk";
    
    /** 请求服务参数 **/
    public static final String REQ_DATA_PARAM = "param";
    
    /////////////////////// 通用参数 //////////////////////////
    /** Api 接口版本 **/
    private String version;
    
    /** Api 请求客户端IP **/
    private String requestIp;
    //////////////////////// 通用参数 /////////////////////////
    
    /** Api 用户userkey **/
    private String userKey;
    
    /** 请求Api Mapping，标识API服务方法 **/
    private String apiMapping;
    
    /** 请求API对象 **/
    private Api api;
    
    /** 请求API参数 **/
    private String param;
    
    /** 请求服务参数值 **/  
    private Object[] args;
    
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getApiMapping() {
        return apiMapping;
    }

    public void setApiMapping(String apiMapping) {
        this.apiMapping = apiMapping;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Request [version=" + version + ", requestIp=" + requestIp + ", userKey=" + userKey
               + ", apiMapping=" + apiMapping + ", api=" + api + ", param=" + param + ", args="
               + Arrays.toString(args) + "]";
    }


}
