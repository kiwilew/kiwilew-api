package org.kiwi.api.core;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/** 
 * Description: API对象<br/>
 *
 * @author kiwilew
 * @date: 2017年3月1日 下午4:34:58
 * @version 1.0
 * @since JDK 1.7
 */
public class Api implements Serializable {
     
    private static final long serialVersionUID = -6379473323026256717L;

    /** api对象 **/  
    private Object bean;
    
    /** api标识，到@Service Bean具体方法的映射 **/  
    private String apiMapping;
    
    /** api方法 **/  
    private Method method;
    
    /** api描述 **/  
    private String desc;
    
    /** api安全，true校验签名 **/  
    private boolean security = false;
    
    /** 零参数方法，true:不含参数,false:含参数 **/  
    private boolean nilArgsMethod = false;
    
    /** 是否空api方法 **/  
    private boolean voidMethod = false;
    
    List<Param> params = new ArrayList<>();

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getApiMapping() {
        return apiMapping;
    }

    public void setApiMapping(String apiMapping) {
        this.apiMapping = apiMapping;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public boolean isSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }
    
    public boolean isVoidMethod() {
        return voidMethod;
    }

    public void setVoidMethod(boolean voidMethod) {
        this.voidMethod = voidMethod;
    }

    public boolean isNilArgsMethod() {
        return nilArgsMethod;
    }

    public void setNilArgsMethod(boolean nilArgsMethod) {
        this.nilArgsMethod = nilArgsMethod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
    
    public /*synchronized*/ void addParam(Param param) {
        if (this.params == null) {
            this.params = new ArrayList<>();
        }
        this.params.add(param);
    }

    @Override
    public String toString() {
        return "Api [bean=" + bean + ", apiMapping=" + apiMapping + ", method=" + method + ", desc="
               + desc + ", security=" + security + ", nilArgsMethod=" + nilArgsMethod
               + ", voidMethod=" + voidMethod + ", params=" + params + "]";
    }

}
