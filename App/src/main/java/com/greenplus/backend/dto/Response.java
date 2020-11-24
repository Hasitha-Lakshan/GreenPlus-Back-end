package com.greenplus.backend.dto;

import org.springframework.stereotype.Service;

@Service
public class Response {

	private String responseBody;
	private boolean responseStatus;

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public boolean isResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(boolean responseStatus) {
		this.responseStatus = responseStatus;
	}

}
