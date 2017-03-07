package org.kiwi.api.core;

import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * Description: API持有者，存放导出的API对象<br/>
 *
 * @author kiwilew
 * @date: 2017年3月2日 下午4:18:38
 * @version 1.0
 * @since JDK 1.7
 */
public class ApiHolder {
    
    private final static Logger logger = LoggerFactory.getLogger(ApiHolder.class);
    
    private static Map<String, Api> holder = new ConcurrentHashMap<>();

    public static void addApi(Api api, RepeatApiPolicy policy) {
        if (api == null) {
            return;
        }
        Api exist = holder.get(api.getApiMapping());
        if (exist == null) {
            holder.put(api.getApiMapping(), api);
            return;
        }
        if (policy == RepeatApiPolicy.coverage) {
            logger.warn("repeat api defined with coverage policy, replace api with new export api service={}, api={} .", api.getBean().getClass(), api.getApiMapping());
            holder.put(api.getApiMapping(), api);
        } else if (policy == RepeatApiPolicy.discard) {
            logger.warn("repeat api defined with discard policy, discard new export api service={}, api={}.", api.getBean().getClass(), api.getApiMapping());
        } else if (policy == RepeatApiPolicy.exception) {
            logger.warn("repeat api defined with exception policy, new export api service={}, api={}.", api.getBean().getClass(), api.getApiMapping());
            throw new RuntimeException(String.format("api named [%s] exist", api.getApiMapping()));
        }
    }
    
    public static Api getApi(String apiMapping) {
        return holder.get(apiMapping);
    }
    
    public static Map<String, Api> getAllApi() {
        return holder;
    }
    
    /** 
     * Description: 重复定义API处理策虐<br/>
     *
     * @author kiwilew
     * @date: 2017年3月2日 下午4:24:16
     * @version 1.0
     * @since JDK 1.7
     */
    public enum RepeatApiPolicy {

        /** 覆盖旧API **/  
        coverage,
        
        /** 丢弃新API **/  
        discard, 
        
        /** 抛异常阻止 **/  
        exception;
    }
}
