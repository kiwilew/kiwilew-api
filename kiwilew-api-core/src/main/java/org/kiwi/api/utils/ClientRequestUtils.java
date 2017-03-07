package org.kiwi.api.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/** 
 * Description: 客户端工具类<br/>
 *
 * @author kiwilew
 * @date: 2017年3月4日 下午5:02:00
 * @version 1.0
 * @since JDK 1.7
 */
public class ClientRequestUtils {

    /** 
     * 获得用户远程地址. <br/> 
     * 
     * @param request
     * @return
     */ 
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Forwarded-For");//request.getHeader("X-Real-IP");//此处可能有坑，多级反向代理时可能会不对
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }
    
    /** 
     * 获取请求Header. <br/> 
     * 
     * @param request
     * @param headerName
     * @return
     */ 
    public static String getRequestHeader(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }

}
