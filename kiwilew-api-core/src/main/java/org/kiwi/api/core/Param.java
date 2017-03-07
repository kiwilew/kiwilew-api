package org.kiwi.api.core;

import java.io.Serializable;
import java.lang.reflect.Type;

/** 
 * Description: API参数<br/>
 *
 * @author kiwilew
 * @date: 2017年3月1日 下午4:34:58
 * @version 1.0
 * @since JDK 1.7
 */
public class Param implements Serializable {

    private static final long serialVersionUID = 8345556114874568268L;

    /** 参数类型 **/  
    private Type type;
    
    /** 参数名称 **/  
    private String name;
    
    ///** 参数值 **/  
    //private Object value;
    
    /** 参数顺序 **/  
    private int index;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Object getValue() {
//        return value;
//    }
//
//    public void setValue(Object value) {
//        this.value = value;
//    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Param [type=" + type + ", name=" + name + ", index=" + index
               + "]";
    }

}
