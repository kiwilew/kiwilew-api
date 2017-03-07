package org.kiwi.demo.service;

import org.kiwi.api.core.ApiExport;
import org.kiwi.api.core.ApiHolder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class DemoService {

    @ApiExport(value="demo.service0", desc="DEMO API 0", security = true)
    public void demo0() {
        System.out.println(JSON.toJSONString(ApiHolder.getAllApi()));
    }
    
    @ApiExport(value="demo.service1", desc="DEMO API 1")
    public String demo1() {
        return JSON.toJSONString(ApiHolder.getAllApi());
    }
    
    @ApiExport(value="demo.service2", desc="DEMO API 2")
    public String demo2(String str, int i) {
        return JSON.toJSONString(ApiHolder.getAllApi());
    }
    
    /*
     *  请求demo
     http://127.0.0.1:8080/kiwilew-api-web/api/service?data={"method":"demo.service3","param":{"mengli":"hualuozhiduoshao","model":{"param1":"param1","param2":"参数2"}},"uk":"userKey"}
     * */
    @ApiExport(value="demo.service3", desc="DEMO API 3")
    public String demo3(String str, DemoModel model) {
        return JSON.toJSONString(ApiHolder.getAllApi());
    }
    
    @ApiExport(value="demo.service4", desc="DEMO API 4")
    public String demo4(String str, DemoModel model, String version, String ip) {
        return JSON.toJSONString(ApiHolder.getAllApi());
    }
    
    public static void main(String[] args) {
        JSONObject json3 = new JSONObject();
        JSONObject param = new JSONObject();
        //param.put("str", "strabc");
        param.put("mengli", "hualuozhiduoshao");
        DemoModel model = new DemoModel();
        model.setParam1("param1");
        model.setParam2("参数2");
        param.put("model", model);
        
        json3.put("uk", "userKey");
        json3.put("method", "demo.service3");
        json3.put("param", param);
        System.out.println(json3.toJSONString());
    }
}
