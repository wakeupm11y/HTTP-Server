package com.MolebohengMahlatji.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

	private HttpParser httpParser;

	@BeforeAll
	public void beforeClass() {
		httpParser = new HttpParser();
	}

	@Test
	void parseHttpRequest() {
		HttpRequest request = null;
		try {
			request = httpParser.parseHttpRequest(
					generateValidTestCase());
		} catch (HttpParsingException e) {
			fail(e);
		}
		assertEquals(request.getMethod(), HttpMethod.GET);
	}

	@Test
	void parseBadHttpRequest() {
		try {
			HttpRequest request = httpParser.parseHttpRequest(
					generateBadTestCase());
			fail();
		} catch (HttpParsingException e) {
			assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
		}
	}

	@Test
	void parseBadHttpRequestMethodLength() {
		try {
			HttpRequest request = httpParser.parseHttpRequest(
					generateBadTestCaseMethodLength());
			fail();
		} catch (HttpParsingException e) {
			assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
		}
	}

	@Test
	void parseBadHttpRequestPathLength() {
		try {
			HttpRequest request = httpParser.parseHttpRequest(
					generateBadTestCasePathLength());
			fail();
		} catch (HttpParsingException e) {
			assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_414_BAD_REQUEST);
		}
	}

	@Test
	void parseBadHttpRequestEmptyRequest() {
		try {
			HttpRequest request = httpParser.parseHttpRequest(
					generateBadTestCaseEmptyRequest());
			fail();
		} catch (HttpParsingException e) {
			assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_414_BAD_REQUEST);
		}
	}

	@Test
	void parseBadHttpRequestOnlyCR() {
		try {
			HttpRequest request = httpParser.parseHttpRequest(
					generateBadTestCaseOnlyCR());
			fail();
		} catch (HttpParsingException e) {
			assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_414_BAD_REQUEST);
		}
	}

	private InputStream generateValidTestCase() {
		String rawData = """
				GET / HTTP/1.1
				Host: localhost:8080
				User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Gecko/20100101 Firefox/145.0
				Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
				Accept-Language: en-US,en;q=0.5
				Accept-Encoding: gzip, deflate, br, zstd
				Connection: keep-alive
				Upgrade-Insecure-Requests: 1
				Sec-Fetch-Dest: document
				Sec-Fetch-Mode: navigate
				Sec-Fetch-Site: none
				Sec-Fetch-User: ?1
				DNT: 1
				Sec-GPC: 1
				Priority: u=0, i
				\r\n
				"""
				.replaceAll("\n", "\r\n");
		InputStream inputStream = new ByteArrayInputStream(
				rawData.getBytes(StandardCharsets.US_ASCII));
		return inputStream;
	}

	private InputStream generateBadTestCase() {
		String rawData = """
				GeT / HTTP/1.1
				Host: localhost:8080
				User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Gecko/20100101 Firefox/145.0
				Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
				Accept-Language: en-US,en;q=0.5
				\r\n
				"""
				.replaceAll("\n", "\r\n");
		InputStream inputStream = new ByteArrayInputStream(
				rawData.getBytes(StandardCharsets.US_ASCII));
		return inputStream;
	}

	private InputStream generateBadTestCaseMethodLength() {
		String rawData = """
				GEET / HTTP/1.1
				Host: localhost:8080
				User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Gecko/20100101 Firefox/145.0
				Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
				Accept-Language: en-US,en;q=0.5
				\r\n
				"""
				.replaceAll("\n", "\r\n");
		InputStream inputStream = new ByteArrayInputStream(
				rawData.getBytes(StandardCharsets.US_ASCII));
		return inputStream;
	}

	private InputStream generateBadTestCasePathLength() {
		String rawData = """
				GET / AAAAAAAAA HTTP/1.1
				Host: localhost:8080
				User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Gecko/20100101 Firefox/145.0
				Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
				Accept-Language: en-US,en;q=0.5
				\r\n
				"""
				.replaceAll("\n", "\r\n");
		InputStream inputStream = new ByteArrayInputStream(
				rawData.getBytes(StandardCharsets.US_ASCII));
		return inputStream;
	}

	private InputStream generateBadTestCaseEmptyRequest() {
		String rawData = """

				Host: localhost:8080
				User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Gecko/20100101 Firefox/145.0
				Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
				Accept-Language: en-US,en;q=0.5
				\r\n
				"""
				.replaceAll("\n", "\r\n");
		InputStream inputStream = new ByteArrayInputStream(
				rawData.getBytes(StandardCharsets.US_ASCII));
		return inputStream;
	}

	private InputStream generateBadTestCaseOnlyCR() {
		String rawData = """
				GET / HTTP/1.1\r
				Host: localhost:8080
				User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Gecko/20100101 Firefox/145.0
				Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
				Accept-Language: en-US,en;q=0.5
				\r\n
				"""
				.replaceAll("\n", "\r\n");
		InputStream inputStream = new ByteArrayInputStream(
				rawData.getBytes(StandardCharsets.US_ASCII));
		return inputStream;
	}

}
