package com.example.alwaysinmem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.alwaysinmem.model.Grave;
import com.example.alwaysinmem.model.Human;
import com.example.alwaysinmem.utils.ConnectionUtils;
import com.example.alwaysinmem.utils.FileUtils;
import com.example.alwaysinmem.utils.RestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DataActivity extends ListActivity {

	public final static String GRAVE_BUNDLE = "GRAVE_BUNDLE";
	
	private Gson gson = new Gson();
	private FileUtils fileUtils = new FileUtils();
	private RestUtils restUtils = new RestUtils();

	private String login;

	private Human human;
	
	private Boolean isConnected;
	
	List<Grave> graves; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_list);
		
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		isConnected = ConnectionUtils.isAppConnected(connectivityManager);
		
		Bundle bundle = getIntent().getExtras();
		login = bundle.getString(LoginActivity.CREDENDIALS);
		
		try {
			human = restUtils.getUser(login);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		String fileContent = fileUtils.openFile(this);
		
//		int lastCommaIdx = fileContent.lastIndexOf(",");

//		fileContent = fileContent.substring(0, lastCommaIdx)
//				+ fileContent.substring(lastCommaIdx + 1, fileContent.length());

		Type listType = new TypeToken<ArrayList<Grave>>() {
		}.getType();

		graves = new ArrayList<Grave>(); //gson.fromJson(fileContent, listType);
		List<Grave> gravesFromServer;
		/*
		try {
			gravesFromServer = restUtils.downloadByLogin(login);
			for (Grave grave : gravesFromServer) {
				if (!graves.contains(grave)) {
					graves.add(grave);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		Grave g = new Grave();
		g.setFirstname("asdf");
		g.setLastname("sdf");
		graves.add(g);
		
		ArrayAdapter<Grave> myAdapter = new ArrayAdapter<Grave>(this, R.layout.data_row_layout, R.id.imie_row, graves);
		
		setListAdapter(myAdapter);
		
		TableLayout table = (TableLayout) this.findViewById(R.id.tableLay);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		registerForContextMenu(getListView());
		/*
		for (final Grave grave : graves) {

			TableRow rowToAdd = new TableRow(this);
			rowToAdd.setLayoutParams(layoutParams);

			TextView nameLbl = new TextView(this);
			nameLbl.setLayoutParams(layoutParams);
			nameLbl.setText(grave.getFirstname() + " " + grave.getLastname());

			Button navBtn = new Button(this);
			navBtn.setLayoutParams(layoutParams);
			navBtn.setText("Nawiguj");

			ImageButton sendBtn = new ImageButton(this);
			sendBtn.setLayoutParams(layoutParams);
			sendBtn.setBackgroundResource(R.drawable.ic_backup);

			Button shareBtn = new Button(this);
			shareBtn.setLayoutParams(layoutParams);
			shareBtn.setText("Dziel");

			shareBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent dataIntent = new Intent(DataActivity.this, ShareActivity.class);
					dataIntent.putExtra(GRAVE_BUNDLE, grave);
					startActivity(dataIntent);
				}
			});

			sendBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					restUtils.send(grave);
				}
			});

			navBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (grave.getLattitude() != null && grave.getLongtitude() != null) {
						Uri gmmIntentUri = Uri
								.parse("google.navigation:q=" + grave.getLattitude() + "," + grave.getLongtitude());
						Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
						mapIntent.setPackage("com.google.android.apps.maps");
						startActivity(mapIntent);
					}
				}
			});

			rowToAdd.addView(nameLbl);
			rowToAdd.addView(navBtn);
			if (isConnected) {
				rowToAdd.addView(sendBtn);
				rowToAdd.addView(shareBtn);
			}
			
			table.addView(rowToAdd, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
		*/
	}
	
	//method for creation of context menu when list item is long clicked
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.data_list_context_menu, menu);
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()){
		case R.id.menu_delete: graves.remove(info.position); Toast.makeText(getBaseContext(), "DELETE", Toast.LENGTH_SHORT).show(); break;
		case R.id.menu_modify: Toast.makeText(getBaseContext(), "MODIFY", Toast.LENGTH_SHORT).show(); break;
		case R.id.menu_navigateTo: navigate((Grave) getListView().getItemAtPosition(info.position)); break;
		case R.id.menu_share: shareInfoAboutGrave((Grave) getListView().getItemAtPosition(info.position)); break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	//List View Item click handling
	@Override
	protected void onListItemClick(android.widget.ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Grave selectedGrave = (Grave) getListView().getItemAtPosition(position);
		Toast.makeText(getBaseContext(), selectedGrave.toString(), Toast.LENGTH_SHORT).show();
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.data, menu);
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
	public void onStart() {
		super.onStart();
	}
	
	private void navigate(Grave grave){
		if (grave.getLattitude() != null && grave.getLongtitude() != null) {
			Uri gmmIntentUri = Uri
					.parse("google.navigation:q=" + grave.getLattitude() + "," + grave.getLongtitude());
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
			mapIntent.setPackage("com.google.android.apps.maps");
			startActivity(mapIntent);
		}
		else 
			Toast.makeText(getBaseContext(), "Informacja o lokalizacja jest nie dostepna", Toast.LENGTH_SHORT).show();
	}
	
	private void shareInfoAboutGrave(Grave grave){
		Intent dataIntent = new Intent(DataActivity.this, ShareActivity.class);
		dataIntent.putExtra(GRAVE_BUNDLE, grave);
		startActivity(dataIntent);
	}

}
