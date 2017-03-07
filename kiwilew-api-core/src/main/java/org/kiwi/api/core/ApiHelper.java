package org.kiwi.api.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kiwi.api.utils.ClientRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

/** 
 * Description: Controller请求辅助对象<br/>
 *
 * @author kiwilew
 * @date: 2017年3月3日 下午8:07:33
 * @version 1.0
 * @since JDK 1.7
 */
public class ApiHelper {
    
    private static Logger logger = LoggerFactory.getLogger(ApiHelper.class);

    public static Request getRequest(HttpServletRequest request, String data) {
        JSONObject json = JSONObject.parseObject(data);
        String apiMapping = json.getString(Request.REQ_DATA_METHOD);
        Api api = ApiHolder.getApi(apiMapping);
        if (api == null) {
            throw new ApiException(Response.Code.not_exist.getCode(), "服务不存在");
        }
        Request apiRequest = new Request();
        apiRequest.setRequestIp(ClientRequestUtils.getRemoteAddr(request));
        apiRequest.setVersion(ClientRequestUtils.getRequestHeader(request, "version"));
        
        apiRequest.setUserKey(json.getString(Request.REQ_DATA_UK));
        apiRequest.setApiMapping(apiMapping);
        apiRequest.setApi(api);
        apiRequest.setParam(json.getString(Request.REQ_DATA_PARAM));
        
        setRequestParams(request, apiRequest);
        return apiRequest;
    }
    
    private static void setRequestParams(HttpServletRequest request, Request apiRequest) {
        Api api = apiRequest.getApi();
        if (api.isNilArgsMethod()) {
            return;
        }
        JSONObject json = StringUtils.isBlank(apiRequest.getParam()) ? null : JSONObject.parseObject(apiRequest.getParam());
        List<Param> params = api.getParams();
        Object[] args = new Object[params.size()];
        for (Param param : params) {
            String name = param.getName();
            Type type = param.getType();
            Object val = TypeUtils.cast(json.get(name), type, ParserConfig.getGlobalInstance());
            args[param.getIndex()] = val;
            
            //特殊非data.param参数赋值
            if ("version".equals(name) && type == String.class) {//接口版本
                args[param.getIndex()] = apiRequest.getVersion();
            } else if ("ip".equals(name) && type == String.class) {//客户端IP
                args[param.getIndex()] = apiRequest.getRequestIp();
            } else if ("userkey".equals(name) && type == String.class) {//userkey
                args[param.getIndex()] = apiRequest.getUserKey();
            }
        }
        apiRequest.setArgs(args);
    }
    
    public static Response invoke(Request apiRequest) {
        Response response = new Response();
        try {
            if (apiRequest.getApi().isVoidMethod()) {
                apiRequest.getApi().getMethod().invoke(apiRequest.getApi().getBean(), apiRequest.getArgs());
            } else {
                Object result = apiRequest.getApi().getMethod().invoke(apiRequest.getApi().getBean(), apiRequest.getArgs());
                response.setResult(result);
            }
        }
        catch (IllegalAccessException e) {
            logger.error("api {} Method.invoke IllegalAccessException args={} , e={}", apiRequest.getApi().getApiMapping(), JSON.toJSON(apiRequest.getArgs()), e);
            throw new ApiException(Response.Code.error.getCode(), "非法访问");
        }
        catch (IllegalArgumentException e) {
            logger.error("api {} Method.invoke IllegalArgumentException args={} , e={}", apiRequest.getApi().getApiMapping(), JSON.toJSON(apiRequest.getArgs()), e);
            throw new ApiException(Response.Code.error.getCode(), "非法参数");
            
        }
        catch (InvocationTargetException e) {
            logger.error("api {} Method.invoke InvocationTargetException args={} , e={}", apiRequest.getApi().getApiMapping(), JSON.toJSON(apiRequest.getArgs()), e);
            throw new ApiException(Response.Code.error.getCode(), "系统异常");
            
        }
        catch (Exception e) {
            logger.error("api {} Method.invoke 发生异常 args={} , e={}", apiRequest.getApi().getApiMapping(), JSON.toJSON(apiRequest.getArgs()), e);
            if (e instanceof ApiException) {
                throw e;
            } else {                
                throw new ApiException(Response.Code.error.getCode(), "系统异常");
            }
            
        }
        response.setCode(Response.Code.succ.getCode());
        return response;
    }
    
    public static void security(Request apiRequest) {
        if (apiRequest.getApi().isSecurity()) {
            logger.warn("please implement validate request security ...");
        }
    }
}
