package com.MolebohengMahlatji.httpserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThread extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

	private int port;
	private String webroot;
	private ServerSocket serverSocket;

	public ServerListenerThread(int port, String webroot) throws IOException {
		this.port = port;
		this.webroot = webroot;
		this.serverSocket = new ServerSocket(this.port);
	}

	@Override
	public void run() {
		try {
			while (serverSocket.isBound() && !serverSocket.isClosed()) {

				Socket socket = this.serverSocket.accept(); // program will waithere until a connection
										// is
				// established
				LOGGER.info("***** Connection accepted: " + socket.getInetAddress());

				HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
				workerThread.start();

			}
			// this.serverSocket.close(); TODO: Handle this later because we want to keep
			// accepting connections

		} catch (IOException e) {
			LOGGER.error("Problem with setting socket", e);
		} finally {
			if (this.serverSocket != null) {
				try {
					this.serverSocket.close();

				} catch (IOException e) {
				}
			}
		}

	}

}
