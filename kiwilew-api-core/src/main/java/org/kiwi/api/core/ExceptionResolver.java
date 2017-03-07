package org.kiwi.api.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.alibaba.fastjson.JSON;

/** 
 * Description: 异常处理<br/>
 *
 * @author kiwilew
 * @date: 2017年3月5日 下午9:07:49
 * @version 1.0
 * @since JDK 1.7
 */
public class ExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        StringBuffer url = request.getRequestURL();
        logger.warn("异常: {}", url, ex);
        if ((handler instanceof HandlerMethod) && ((HandlerMethod) handler).getMethodAnnotation(ResponseBody.class) != null) {
            Response result = new Response();
            //String userKey = (String) request.getAttribute("uk");
            if (ex instanceof ApiException) {
                result.setCode(((ApiException) ex).getCode());
                result.setMsg(((ApiException) ex).getMsg());
            } else {
                result.setCode(Response.Code.error.getCode());
                result.setMsg("系统异常");
            }
            PrintWriter wr = null;
            try {
                response.setContentType("text/html;charset=UTF-8"); 
                wr = response.getWriter();
                wr.write(JSON.toJSONString(result));
                wr.flush();
            }
            catch (IOException e) {
                logger.warn("response write异常: {}", url, ex);
            } finally {
                if (wr != null) {
                    wr.close();
                }                
            }
            return null;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return null;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
