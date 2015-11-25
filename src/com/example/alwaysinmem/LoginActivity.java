package com.example.alwaysinmem;

import com.example.alwaysinmem.model.Human;
import com.example.alwaysinmem.utils.RestUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public static final String CREDENDIALS = "LOGIN";
	public static final String ANONYMOUS = "ANONYMOUS";

	private Button loginBtn;

	private String login;
	private String password;

	RestUtils restUtils = new RestUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (!isConnected()) {
			Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
			mainIntent.putExtra(CREDENDIALS, ANONYMOUS);
			startActivity(mainIntent);
		}

		loginBtn = (Button) findViewById(R.id.loginBtn);

		loginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Human auth;

				login = ((EditText) findViewById(R.id.login)).getText().toString();
				password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();

				try {
					auth = restUtils.auth(login, password);

					if (auth != null) {
						Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
						mainIntent.putExtra(CREDENDIALS, auth.getLogin());
						startActivity(mainIntent);
					} else {
						Toast.makeText(getBaseContext(), "Nie prawidłowy login lub hasło", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private Boolean isConnected() {
		ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = connectionManager.getNetworkInfo(0).getState();
		State wifi = connectionManager.getNetworkInfo(1).getState();

		return (mobile == State.CONNECTED || mobile == State.CONNECTING || wifi == State.CONNECTED
				|| wifi == State.CONNECTING || wifi == State.CONNECTING);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
