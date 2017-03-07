package org.kiwi.api.demo.controller;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.kiwi.api.core.ApiHelper;
import org.kiwi.api.core.Request;
import org.kiwi.api.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/** 
 * Description: API服务入口<br/>
 * Description: 通过该Controller向外暴露Service服务方法<br/>
 *
 * @author kiwilew
 * @date: 2017年3月3日 下午11:00:02
 * @version 1.0
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    private static Logger logger = LoggerFactory.getLogger(ApiController.class);
    
    @RequestMapping("/service")
    @ResponseBody
    public Response service(HttpServletRequest request, String data) {
        long begin = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString() + System.nanoTime() + new Random().nextInt(10000);
        if (logger.isDebugEnabled()) {
            logger.debug("api service requestId={}请求参数 data={}, requestparams={}", requestId, data, JSON.toJSONString(request.getParameterMap()));
        }
        Request apiReq = ApiHelper.getRequest(request, data);
        ApiHelper.security(apiReq);
        Response response = ApiHelper.invoke(apiReq);
        if (logger.isDebugEnabled()) {
            logger.debug("api service requestId={}返回结果response={}", requestId, JSON.toJSONString(response));
        }
        logger.info("api service requestId={}请求耗时{}ms", requestId, System.currentTimeMillis() - begin);
        
        return response;
    }
}
