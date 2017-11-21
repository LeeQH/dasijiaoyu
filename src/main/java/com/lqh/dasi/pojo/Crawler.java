package com.lqh.dasi.pojo;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Crawler {
	private CloseableHttpClient httpClient;
	private HttpClientContext context;
	private CookieStore cookieStore;
	private RequestConfig requestConfig;
	private CloseableHttpResponse httpResponse;
	
	public Crawler(){
		init();
	}
	
	public Crawler init(){
		context = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
		requestConfig = RequestConfig.custom()
				.setConnectTimeout(120000)
				.setSocketTimeout(60000)
				.setConnectionRequestTimeout(60000)
				.build();
		// 设置默认跳转以及存储cookie
		httpClient = HttpClientBuilder.create()
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy())
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore).build();
		return this;
	}
	
	public void close(){
		try {
			httpClient.close();
			httpResponse.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("\n-------------COOKIE-------------"+"\n");
		Cookie cookie=cookieStore.getCookies().get(0);
		sb.append("cookieName:"+cookie.getName()+"\n");
		sb.append("cookieValue:"+cookie.getValue()+"\n");
		sb.append("domain:"+cookie.getDomain()+"\n");
		sb.append("path:"+cookie.getPath()+"\n");
		
		sb.append("\n------------RESPONSE------------"+"\n");
		sb.append("state:"+httpResponse.getStatusLine().getStatusCode()+"\n");
		try {
			sb.append(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	public CloseableHttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(CloseableHttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	public HttpClientContext getContext() {
		return context;
	}
	public void setContext(HttpClientContext context) {
		this.context = context;
	}
	public CookieStore getCookieStore() {
		return cookieStore;
	}
	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}
	public RequestConfig getRequestConfig() {
		return requestConfig;
	}
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	
}
