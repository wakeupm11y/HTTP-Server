package com.MolebohengMahlatji.httpserver.config;

public class Config {

	private int port;
	private String webroot;

	public int getPort() {
		return this.port;
	}

	public String getWebroot() {
		return this.webroot;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setWebroot(String webroot) {
		this.webroot = webroot;
	}

}
