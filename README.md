#kiwilew-api
特点：
1.统一API请求；
2.方便服务发布；
3.让开发专注业务业务实现。

依赖spring启动自动暴露服务，向统一api请求对外发布服务。
你只需着力业务代码编写，只需在业务服务类Bean需要发布的方法上添加注解@ApiExport即可在启动web工程时发布服务。ApiExport注解value表示服务唯一标识，security表示是否做安全校验，true是需要校验，校验逻辑请根据实际情况扩展ApiHelper.security实现，desc表示接口的简单描述信息，用来导出文档使用。

#示例：
#1.编写一个不叫HelloWorld的HelloWorld
package org.kiwi.demo.service;

import org.kiwi.api.core.ApiExport;
import org.kiwi.api.core.ApiHolder;
import org.springframework.stereotype.Service;
//Api服务依赖spring bean，交给spring容器管理bean
@Service
public class DemoService {
	//通过spring bean中方法上添加注解发布服务,改服务唯一标识method为demo.myservice0,需要做安全校验
    @ApiExport(value="demo.myservice0", desc="DEMO API 0", security = true)
    public int mydemo0(int paramName) {
        return paramName + 10;
    }
}

当然，你的web工程你需要有个ApiController的服务，请参考ApiController改写。

#2.在工程spring配置xml文件中引入api-core.xml，不然谁认识你的@ApiExport注解来发布api服务呢？即添加该引用：
<import resource="classpath*:api-core.xml"/>

#3.请求参数非参数信息放入http header（如版本号version），参数项JSON格式：
{
	"method":"demo.myservice0",#请求api唯一标识，@ApiExport value值
	"uk":"userKey",#用户标识，用来识别用户
	"param":{
				"paramName":"5",#基本数据类型
				"user":{"name":"kiwilew","language":"java","password":"123456"}#非基础数据类型，值转JSON
			}#请求api方法从客户端发送的请求参数，key值与api方法参数名需保持一致
}

#4.请求DEMO示例：http://127.0.0.1:8080/kiwilew-api-web/api/service?data={"method":"demo.myservice0","param":{"paramName":"5"},"uk":"userKey"}

#更多示例请参考org.kiwi.demo.service.DemoService

有点懒，maven依赖有不需要的jar，可自行删除。