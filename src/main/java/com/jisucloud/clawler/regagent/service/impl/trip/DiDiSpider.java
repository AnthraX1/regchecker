package com.jisucloud.clawler.regagent.service.impl.trip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;


@PapaSpiderConfig(
		home = "didiglobal.com", 
		message = "滴滴出行是涵盖出租车、专车、滴滴快车、顺风车、代驾及大巴等多项业务在内的一站式出行平台，2015年9月9日由“滴滴打车”更名而来。", 
		platform = "dudichuxing", 
		platformName = "滴滴出行", 
		tags = { "出行" , "打车" , "顺风车" }, 
		testTelephones = { "18515290000", "18212345678" })
public class DiDiSpider extends PapaSpider {


    private Headers getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Android/4.4.2 didihttp OneNet/2.1.0.81 com.sdu.didi.psnger/5.2.40");
        headers.put("didi-header-hint-content", "{\"utc_offset\":\"480\",\"lang\":\"zh-CN\",\"Cityid\":1,\"app_timeout_ms\":20000}");
        headers.put("Host", "open.taou.com");
        headers.put("TripCountry", "CN");
        headers.put("didi-header-omgid", "EacWj8FNRHm5w-r08OP1ww");
        headers.put("didi-header-rid", "1193bf6d5c8a0cb5000031fe02345592");
        headers.put("Productid", "260");
        headers.put("CityId", "1");
        headers.put("Host", "epassport.diditaxi.com.cn");
        headers.put("_ddns_", "1");
        headers.put("wsgsig", "dd04-3HBHNGZXPUK3U3DbK6sLXYv4tMcQI7B8UZ58HbABh/lSUBpiOpOAU3c5V7uLw5zSR5tbvPpqtNyqs7XctcE1uoEQF7v73zwlnDHb4TEnDa3Cvpvxv8U/WJPul+QIsHv8fnqpyI9/ms2xzTgUn+p8v2zWtziKwTcTZ7KgwLpZtauizx1mnicx135nVCc7yqA+nsncx2Rtt+CNxq");
        return Headers.of(headers);
    }

    private FormBody getParams(String mobile) {
    	JSONObject q = new JSONObject();
        q.put("cell", mobile);
        q.put("api_version", "1.0.1");
        q.put("app_version", "5.2.40");
        q.put("suuid", "F69BCA36BA3EB223A483686928412AA2");
        q.put("canonical_country_code", "CN");
        q.put("channel", "16");
        q.put("os", "4.4.2");
        q.put("country_calling_code", "+86");
        q.put("network_type", "WIFI");
        q.put("imei", "86439401008109198353309030EBC921183A280F87EE19B");
        q.put("lang", "zh-CN");
        q.put("model", "XT1570");
        q.put("map_type", "soso");
        q.put("lng", 116.46716211917766);
        q.put("lat", 39.697648728975044);
        q.put("country_id", 156);
        q.put("role", 1);
        q.put("scene", 7);
        q.put("appid", 10000);
    	FormBody formBody = new FormBody.Builder()
        .add("q", q.toJSONString())
        .build();
        return formBody;
    }

    
    public boolean checkTelephone(String account) {
        try {
            String url = "https://epassport.diditaxi.com.cn/passport/login/v5/getIdentity";
            Request request = new Request.Builder().url(url)
        			.headers(getHeader())
        			.post(getParams(account))
					.build();
            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                JSONObject result = JSON.parseObject(response.body().string());
                if (result.getIntValue("errno") == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean checkEmail(String account) {
        return false;
    }

    
    public Map<String, String> getFields() {
        return null;
    }

    
    

    
	
    
	
	
}
