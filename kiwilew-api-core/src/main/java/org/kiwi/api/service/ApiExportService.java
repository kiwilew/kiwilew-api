package org.kiwi.api.service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.kiwi.api.core.Api;
import org.kiwi.api.core.ApiExport;
import org.kiwi.api.core.ApiHolder;
import org.kiwi.api.core.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Service;

@Service
public class ApiExportService implements InitializingBean, ApplicationContextAware {
    
    private final static Logger logger = LoggerFactory.getLogger(ApiExportService.class);

    private ApplicationContext applicationContext;
    
    private final static ParameterNameDiscoverer paramDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    
    
    private void exportApiFromSpingApplicationContext() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        if (beanNames == null || beanNames.length == 0) {
            logger.warn("get 0 bean definition name from spring applicationContext, end export api.");
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("get {} beans from spring applicationContext, begin export api.", beanNames.length);
        }
        
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Class<? extends Object> clazz = bean.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            if (methods == null || methods.length == 0) {
                logger.info("skip export api from bean {} with 0 declared methods.", clazz.getName());
            }
            for (Method candidate : methods) {
                ApiExport apiExport = candidate.getAnnotation(ApiExport.class);
                if (apiExport != null) {
                    exportApis(bean, candidate, apiExport);
                }
            }
        }
        
        
        if (logger.isDebugEnabled()) {
            logger.debug("export {} api, end export api.");
        }
        
    }
    
    private void exportApis(Object springBean, Method apiMethod, ApiExport apiExport) {
        Type[] paramTypes = apiMethod.getGenericParameterTypes();
        Api api = new Api();
        api.setBean(springBean);
        api.setMethod(apiMethod);
        api.setDesc(api.getDesc());
        api.setApiMapping(apiExport.value());
        api.setSecurity(apiExport.security());
        Class<?> returnType = apiMethod.getReturnType();
        if (returnType.equals(void.class)) {
            api.setVoidMethod(true);
        }
        if (paramTypes == null || paramTypes.length == 0) {
            api.setNilArgsMethod(true);   
        } else {
            String[] paramNames = paramDiscoverer.getParameterNames(apiMethod);
            int paramIdx = 0;
            for (String paramName : paramNames) {
                Param param = new Param();
                param.setType(paramTypes[paramIdx]);
                param.setName(paramName);
                param.setIndex(paramIdx);
                api.addParam(param);
                paramIdx++;
            }
        }
        ApiHolder.addApi(api, ApiHolder.RepeatApiPolicy.exception);
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        exportApiFromSpingApplicationContext();
    }

    
}
