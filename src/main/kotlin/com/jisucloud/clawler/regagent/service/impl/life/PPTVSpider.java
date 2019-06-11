package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PPTVSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "PPTV是国内领先的综合视频门户网站平台,视频内容丰富多元,包括电视剧、电影、动漫、综艺、体育、娱乐、游戏、搞笑、旅游、财富、少儿、教育、音乐、直播、原创等。";
	}

	@Override
	public String platform() {
		return "pptv";
	}

	@Override
	public String home() {
		return "pptv.com";
	}

	@Override
	public String platformName() {
		return "PPTV";
	}

	@Override
	public String[] tags() {
		return new String[] {"视频", "影音"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new PPTVSpider().checkTelephone("18720982607"));
//		System.out.println(new PPTVSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://api.passport.pptv.com/checkLogin?cb=checklogin&loginid="+account+"&sceneFlag=2&channel=208000202035&format=jsonp&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "api.passport.pptv.com")
					.addHeader("Referer", "https://i.pptv.com/h5user/login?target=new&back=%2F%2Fi.pptv.com%2Fh5user%2Flogin%2Fpg_login_success%3Fback%3Dhttps%3A%2F%2Fm.pptv.com%2F&type=login&mobile=1&tab=login")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("不存在")) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
