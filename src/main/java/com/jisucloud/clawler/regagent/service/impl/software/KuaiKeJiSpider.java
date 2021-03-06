package com.jisucloud.clawler.regagent.service.impl.software;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "mydrivers.com", 
		message = "快科技(驱动之家旗下媒体)为您提供第一手的科技新闻资讯、产品评测、驱动下载等服务。老牌的驱动下载频道通过方便快捷的驱动分类、搜索服务,助您快速找到所需的驱动。", 
		platform = "mydrivers", 
		platformName = "快科技", 
		tags = { "系统驱动", "科技" ,"软件" }, 
		testTelephones = { "15510257873", "18212345678" })
public class KuaiKeJiSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://passport.mydrivers.com/login.aspx");smartSleep(2000);
			chromeDriver.findElementById("txtusername").sendKeys(account);
			chromeDriver.findElementById("txtpassword").sendKeys("xas12qw1dsadsa");
			chromeDriver.findElementById("login").click();smartSleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

	@Override
	public HookTracker getHookTracker() {
		return HookTracker.builder().addUrl("v2/m/action/userlogin.aspx").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		checkTel = contents.getTextContents().contains("密码");
	}

}
