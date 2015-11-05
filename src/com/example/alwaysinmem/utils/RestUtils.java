package com.example.alwaysinmem.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.alwaysinmem.model.Grave;

public class RestUtils {

	private static final String BASE_URL = "http://192.168.1.7:8080";

	private RestTemplate restTemplate = new RestTemplate();

	public void send(final Grave grave) {
		final String url = BASE_URL + "/grave";

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity entity = new HttpEntity(grave, headers);
				ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
				// String response = restTemplate.postForObject(url, grave,
				// String.class);
			}
		});
		thread.setPriority(156);
		thread.start();
	}

	public void download() {
		String url = BASE_URL + "/grave";

		restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());

		String response = restTemplate.getForObject(url, String.class);
	}

}
