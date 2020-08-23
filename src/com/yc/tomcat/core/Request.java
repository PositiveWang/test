package com.yc.tomcat.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
	private String url;//请求资源地址
	private String method; //请求方式
	private String protoalVersion; //协议版本
	private InputStream is = null;
	private Map<String, String> parameters = new HashMap<String, String>();
	
	public Request(InputStream is) {
		this.is = is;
		parse();//解析请求
	}

	/**
	 * 解析请求的方法
	 */
	private void parse() {
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(is));
			String line = null;
			/*int flag = 0;
			while( (line = read.readLine()) != null ) {
				System.out.println(line);
				if( flag == 0 ) {//说明是起始行
					String[] strs = line.split(" ");
					this.method = strs[0];
					this.protoalVersion = strs[2];
					if( "GET".equals(method) ) {
						doGet(strs[1]);//处理get请求
					}
				}
				flag ++;
			}*/
			if( (line = read.readLine()) != null ) {
				String[] strs = line.split(" ");
				this.method = strs[0];
				this.protoalVersion = strs[2];
				if( "GET".equals(method) ) {
					doGet(strs[1]);//处理get请求
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理get请求的方法
	 * @param string
	 */
	private void doGet(String str) {
		//先处理请求参数
		if( !str.contains("?") ) {
			this.url = str;
			return;
		}
		
		//如果有参数解析参数，截取，分割
		String paramStr = str.substring(str.indexOf("?") + 1);
		String[] params = paramStr.split("&");
		String[] param = null;
		
		for( String strs : params ) {
			param = strs.split("=");
			this.parameters.put(param[0], param[1]);
		}
		this.url = str.substring(0, str.indexOf("?"));
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getProtoalVersion() {
		return protoalVersion;
	}
	
	public String getParameters(String key) {
		return this.parameters.get(key);
	}
	
	
}
