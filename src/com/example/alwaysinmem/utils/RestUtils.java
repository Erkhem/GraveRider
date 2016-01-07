package com.example.alwaysinmem.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.alwaysinmem.dto.HumanDTO;
import com.example.alwaysinmem.model.Grave;
import com.example.alwaysinmem.model.Human;

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

	public Human auth(final String login, final String pass) throws InterruptedException, ExecutionException {
		AsyncTask<String, Void, Human> result = new HttpRequestTaskAuth().execute(login, pass);

		return result.get();
	}

	public List<Grave> downloadByLogin(String login) throws InterruptedException, ExecutionException {
		AsyncTask<String, Void, List<Grave>> result = new HttpRequestTaskGet().execute(login);
		
		return result.get();
	}

	public List<String> downloadLogins() throws InterruptedException, ExecutionException {
		AsyncTask<Void, Void, List<String>> result = new HttpRequestTaskGetLogins().execute();

		return result.get();
	}

	public Human getUser(String login) throws InterruptedException, ExecutionException {
		AsyncTask<String, Void, Human> result = new HttpRequestHumanGet().execute(login);

		return result.get();
	}

	public void updateGravesOwners(Grave graveToShare) {
		graveToSend = graveToShare;
		new HttpRequestTaskPost().execute();
	}

	private class HttpRequestHumanGet extends AsyncTask<String, Void, Human> {

		@Override
		protected Human doInBackground(String... params) {
			try {
				final String url = BASE_URL + "/human/" + params[0];
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				Human human = restTemplate.getForObject(url, Human.class);
				return human;
			} catch (Exception e) {
				Log.e("MainActivity", e.getMessage(), e);
			}
			return null;
		}
	}

	private class HttpRequestTaskGet extends AsyncTask<String, Void, List<Grave>> {

		@Override
		protected List<Grave> doInBackground(String... params) {
			try {
				final String url = BASE_URL + "/grave/" + params[0];
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

	private class HttpRequestTaskGetLogins extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			try {
				final String url = BASE_URL + "/human";
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				List<String> grave = Arrays.asList(restTemplate.getForObject(url, String[].class));
				return grave;
			} catch (Exception e) {
				Log.e("ShareActivity", e.getMessage(), e);
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
				requestHeaders.setContentType(new MediaType("application", "json"));
				HttpEntity<Grave> requestEntity = new HttpEntity<Grave>(graveToSend, requestHeaders);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

				ResponseEntity<Grave[]> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
						Grave[].class);
			} catch (Exception e) {
				Log.e("MainActivity", e.getMessage(), e);
			}
			return null;
		}
	}

	private class HttpRequestTaskAuth extends AsyncTask<String, Void, Human> {

		@Override
		protected Human doInBackground(String... loginData) {
			try {
				final String url = BASE_URL + "/login";

				HumanDTO requestBody = new HumanDTO(loginData[0], loginData[1]);

				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(new MediaType("application", "json"));
				HttpEntity<HumanDTO> requestEntity = new HttpEntity<HumanDTO>(requestBody, requestHeaders);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

				ResponseEntity<Human> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
						Human.class);

				if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
					return responseEntity.getBody();
				}
			} catch (Exception e) {
				Log.e("LoginActivity", e.getMessage(), e);
			}
			return null;
		}
	}

}
