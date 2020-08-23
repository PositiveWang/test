package com.yc.tomcat.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerSession implements Runnable {
	private Socket sk = null;
	private OutputStream os = null;
	
	public ServerSession(Socket sk) {
		this.sk = sk;
	}
	
	
	@Override
	public void run() {
		try ( InputStream is = sk.getInputStream() ){
			Request request = new Request(is);//处理请求
			
			//获取请求地址的资源地址
			String url = request.getUrl();
			//System.out.println(url);
			
			this.os = sk.getOutputStream();
			
			//响应
			new Response(os).sendRedirect(url);
		} catch (IOException e) {
			send500(e);
			e.printStackTrace();
		}
	}

	/**
	 * 发送500错误信息
	 * @param e
	 */
	private void send500(IOException e) {
		
	}
}
