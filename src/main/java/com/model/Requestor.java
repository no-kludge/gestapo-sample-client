package com.model;

import org.springframework.web.client.RestTemplate;

public class Requestor {
	public String getMessage() {
		RestTemplate rt = new RestTemplate();
		
		String s = rt.getForObject("http://localhost:8088/lookuptest", String.class);
		
		return s;
	}
}
