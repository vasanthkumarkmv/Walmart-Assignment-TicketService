package com.walmart.ticketservice.exception;

import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class CustomRestClientResponseException {

	private static final long serialVersionUID = -8803556342728481792L;

	private static final String DEFAULT_CHARSET = "ISO-8859-1";


	private final int rawStatusCode;

	private final String statusText;

	private final byte[] responseBody;

	private final HttpHeaders responseHeaders;

	private final String responseCharset;


	public CustomRestClientResponseException(String message, int statusCode, String statusText,
                                             HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {

		this.rawStatusCode = statusCode;
		this.statusText = message;
		this.responseHeaders = responseHeaders;
		this.responseBody = (responseBody != null ? responseBody : new byte[0]);
		this.responseCharset = (responseCharset != null ? responseCharset.name() : DEFAULT_CHARSET);
	}


	/**
	 * Return the raw HTTP status code value.
	 */
	public int getRawStatusCode() {
		return this.rawStatusCode;
	}

	/**
	 * Return the HTTP status text.
	 */
	public String getStatusText() {
		return this.statusText;
	}

	/**
	 * Return the HTTP response headers.
	 */
	public HttpHeaders getResponseHeaders() {
		return this.responseHeaders;
	}

	/**
	 * Return the response body as a byte array.
	 */
	public byte[] getResponseBodyAsByteArray() {
		return this.responseBody;
	}

	/**
	 * Return the response body as a string.
	 */
	public String getResponseBodyAsString() {
		try {
			return new String(this.responseBody, this.responseCharset);
		}
		catch (UnsupportedEncodingException ex) {
			// should not occur
			throw new IllegalStateException(ex);
		}
	}
}
