package com.example.alwaysinmem;

import org.json.JSONException;

import com.example.alwaysinmem.model.Grave;
import com.example.alwaysinmem.model.Human;
import com.example.alwaysinmem.utils.FileUtils;
import com.example.alwaysinmem.utils.RestUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	private Button localizationBtn;
	private Button saveBtn;
	private Button dataBtn;

	private LocationManager locationManager;

	private String lattitude;
	private String longtitude;

	private FileUtils fileUtils = new FileUtils();
	
	private String login;
	
	private RestUtils restUtils = new RestUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle extras = getIntent().getExtras();
		login = extras.getString(LoginActivity.CREDENDIALS);
		
		View.OnClickListener getDataActivityListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent dataIntent = new Intent(MainActivity.this, DataActivity.class);
				dataIntent.putExtra(LoginActivity.CREDENDIALS, login);
				startActivity(dataIntent);
			}
		};

		View.OnClickListener getLocationListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MainActivity.this);
			}
		};

		View.OnClickListener saveListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String firstname = ((EditText) findViewById(R.id.imieInput)).getText().toString();
				final String lastname = ((EditText) findViewById(R.id.nazwiskoInput)).getText().toString();

				Grave grave = new Grave();

				grave.setFirstname(firstname);
				grave.setLastname(lastname);
				grave.setLongtitude(longtitude);
				grave.setLattitude(lattitude);
				
				Human human;
				try {
					human = restUtils.getUser(login);
					grave.getOwners().add(human);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					restUtils.send(grave);
//					fileUtils.saveFile(grave, MainActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
					Log.e("ERROR", "Błąd podczas zapisu");
				}
			}
		};

		localizationBtn = (Button) findViewById(R.id.localizationBtn);
		saveBtn = (Button) findViewById(R.id.saveBtn);
		dataBtn = (Button) findViewById(R.id.dataBtn);

		localizationBtn.setOnClickListener(getLocationListener);
		saveBtn.setOnClickListener(saveListener);
		dataBtn.setOnClickListener(getDataActivityListener);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		lattitude = String.valueOf(location.getLatitude());
		longtitude = String.valueOf(location.getLongitude());
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Włączono GPS " + provider, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "WYłączono GPS " + provider, Toast.LENGTH_SHORT).show();
	}

}
