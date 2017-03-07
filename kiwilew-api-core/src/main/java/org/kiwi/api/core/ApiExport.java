package org.kiwi.api.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Description: API导出注解<br/>
 *
 * @author kiwilew
 * @date: 2017年3月1日 下午4:49:21
 * @version 1.0
 * @since JDK 1.7
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExport {

    /** api服务唯一标识 */
    String value();

    /** 安全校验标识，true校验 */
    boolean security() default false;

    /** api说明 */
    String desc() default "";
}
