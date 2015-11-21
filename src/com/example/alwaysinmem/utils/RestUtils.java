package com.example.alwaysinmem.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.alwaysinmem.model.Grave;

import android.os.AsyncTask;
import android.util.Log;

public class RestUtils {

	private static final String BASE_URL = "http://192.168.1.7:8080";

	private RestTemplate restTemplate = new RestTemplate();
	private Grave graveToSend;

	public void send(final Grave grave) {
		graveToSend = grave;
		new HttpRequestTaskPost().execute();
	}

	public void download() {
		new HttpRequestTaskGet().execute();
	}

	private class HttpRequestTaskGet extends AsyncTask<Void, Void, List<Grave>> {

		@Override
		protected List<Grave> doInBackground(Void... params) {
			try {
				final String url = BASE_URL + "/grave";
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				List<Grave> grave = Arrays.asList(restTemplate.getForObject(url, Grave[].class));
				return grave;
			} catch (Exception e) {
				Log.e("MainActivity", e.getMessage(), e);
			}
			return null;
		}

	}

	private class HttpRequestTaskPost extends AsyncTask<Void, Void, List<Grave>> {

		@Override
		protected List<Grave> doInBackground(Void... params) {
			try {
				final String url = BASE_URL + "/grave";
				
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(new MediaType("application","json"));
				HttpEntity<Grave> requestEntity = new HttpEntity<Grave>(graveToSend, requestHeaders);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

				ResponseEntity<Grave[]> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Grave[].class);
			} catch (Exception e) {
				Log.e("MainActivity", e.getMessage(), e);
			}
			return null;
		}
	}

}
