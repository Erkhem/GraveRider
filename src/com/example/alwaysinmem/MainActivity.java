package com.example.alwaysinmem;

import com.example.alwaysinmem.model.Grave;
import com.example.alwaysinmem.model.Human;
import com.example.alwaysinmem.utils.ConnectionUtils;
import com.example.alwaysinmem.utils.FileUtils;
import com.example.alwaysinmem.utils.RestUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TableRow.LayoutParams;
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

	private Boolean isConnected;
	
	private ProgressBar spinner;
	private ImageButton tickBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		isConnected = ConnectionUtils.isAppConnected(connectivityManager);

		Bundle extras = getIntent().getExtras();
		login = extras.getString(LoginActivity.CREDENDIALS);

		localizationBtn = (Button) findViewById(R.id.localizationBtn);
		saveBtn = (Button) findViewById(R.id.saveBtn);
		dataBtn = (Button) findViewById(R.id.dataBtn);
   	 	tickBtn = (ImageButton) findViewById(R.id.tickBtn);
		
		spinner = (ProgressBar) findViewById(R.id.progressBar1);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		View.OnClickListener getDataActivityListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent dataIntent = new Intent(MainActivity.this, DataActivity.class);
				dataIntent.putExtra(LoginActivity.CREDENDIALS, login);
				startActivity(dataIntent);
				/* DELETE COMMENT
				if (isConnected) {
					Intent dataIntent = new Intent(MainActivity.this, DataActivity.class);
					dataIntent.putExtra(LoginActivity.CREDENDIALS, login);
					startActivity(dataIntent);
				} else {
					Toast.makeText(MainActivity.this, "Najpierw włącz internet", Toast.LENGTH_LONG).show();
				}
				*/
			}
		};

		View.OnClickListener getLocationListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				
				tickBtn.setVisibility(View.GONE);
				spinner.setVisibility(View.VISIBLE);
				
				Handler handler = new Handler(); 
			    handler.postDelayed(new Runnable() { 
			         public void run() { 
			        	 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MainActivity.this);
			        	 Toast.makeText(MainActivity.this, "Pobrano lokalizację", Toast.LENGTH_SHORT).show();
			        	 spinner.setVisibility(View.GONE);

			        	 tickBtn.setVisibility(View.VISIBLE);
			         } 
			    }, 1500); 
				
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
					if (isConnected) {
						restUtils.send(grave);
					}
					Toast.makeText(MainActivity.this, "Zapisano !", Toast.LENGTH_SHORT).show();
				} catch (Exception e1) {
					e1.printStackTrace();
					Log.e("ERROR", "Błąd podczas zapisu");
				}

			}
		};

		localizationBtn.setOnClickListener(getLocationListener);
		saveBtn.setOnClickListener(saveListener);
		dataBtn.setOnClickListener(getDataActivityListener);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isConnected) {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
