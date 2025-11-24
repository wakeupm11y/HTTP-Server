package com.MolebohengMahlatji.httpserver.core;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HttpConnectionWorkerThread extends Thread {
	private static final String CRLF = "\n\r";
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

	private Socket socket;

	public HttpConnectionWorkerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {

			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			// TODO: we should do the reading

			int _byte;
			while ((_byte = inputStream.read()) >= 0) {
				System.out.print((char) _byte);
			}

			// TODO: we would do the writing
			String html = "<html><head><title>Simple Java HTTP Server</title><body><h1>This page was serverd using my simple http server</h1></body></head></html>";
			String response = "HTTP/1.1 200 OK" + CRLF + "Content-Length: " + html.getBytes().length
					+ CRLF
					+ CRLF + html + CRLF + CRLF; // Status Line: <HTTP VERSION> <RESPONSE
			// CODE>
			// <RESPONSE MESSAGE>
			outputStream.write(response.getBytes());
			LOGGER.info("Connection processing finished.");

		} catch (IOException e) {
			LOGGER.info("Error with communication", e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}

	}
}
