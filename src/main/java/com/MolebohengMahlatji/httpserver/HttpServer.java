package com.MolebohengMahlatji.httpserver;

import com.MolebohengMahlatji.httpserver.config.ConfigManager;
import com.MolebohengMahlatji.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import com.MolebohengMahlatji.httpserver.config.Config;

public class HttpServer {
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

	/**
	 * Driver program for the server
	 */
	public static void main(String[] args) {
		LOGGER.info("Starting server...");

		ConfigManager.getInstance().loadConfigFile("src/main/resources/http.json");
		Config conf = ConfigManager.getInstance().getCurrentConfig();

		LOGGER.info("Using Port: " + conf.getPort());
		LOGGER.info("Using webroot: " + conf.getWebroot());

		try {
			ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(),
					conf.getWebroot());
			serverListenerThread.start();

		} catch (IOException e) {
			e.printStackTrace();
			// TODO: handle later
		}
	}

}
