package com.example.alwaysinmem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.alwaysinmem.model.Grave;
import com.example.alwaysinmem.utils.FileUtils;
import com.example.alwaysinmem.utils.RestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class DataActivity extends Activity {

	private Gson gson = new Gson();
	private FileUtils fileUtils = new FileUtils();
	private RestUtils httpUtils = new RestUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);

		String fileContent = fileUtils.openFile(this);

		int lastCommaIdx = fileContent.lastIndexOf(",");

		fileContent = fileContent.substring(0, lastCommaIdx)
				+ fileContent.substring(lastCommaIdx + 1, fileContent.length());

		Type listType = new TypeToken<ArrayList<Grave>>() {
		}.getType();
		List<Grave> graves = gson.fromJson(fileContent, listType);

		TableLayout table = (TableLayout) this.findViewById(R.id.tableLay);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		for (final Grave grave : graves) {

			TableRow rowToAdd = new TableRow(this);
			rowToAdd.setLayoutParams(layoutParams);

			TextView nameLbl = new TextView(this);
			nameLbl.setLayoutParams(layoutParams);
			nameLbl.setText(grave.getFirstname() + " " + grave.getLasename());

			Button navBtn = new Button(this);
			navBtn.setLayoutParams(layoutParams);
			navBtn.setText("Nawiguj");

			ImageButton sendBtn = new ImageButton(this);
			sendBtn.setLayoutParams(layoutParams);
			sendBtn.setBackgroundResource(R.drawable.ic_backup);

			sendBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					httpUtils.send(grave);
				}
			});

			navBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (grave.getLattitute() != null && grave.getLongtitute() != null) {
						Uri gmmIntentUri = Uri
								.parse("google.navigation:q=" + grave.getLattitute() + "," + grave.getLongtitute());
						Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
						mapIntent.setPackage("com.google.android.apps.maps");
						startActivity(mapIntent);
					}
				}
			});

			rowToAdd.addView(nameLbl);
			rowToAdd.addView(navBtn);
			rowToAdd.addView(sendBtn);

			table.addView(rowToAdd, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

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
}
