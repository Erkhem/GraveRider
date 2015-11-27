package com.example.alwaysinmem;

import java.util.List;

import com.example.alwaysinmem.model.Grave;
import com.example.alwaysinmem.model.Human;
import com.example.alwaysinmem.utils.RestUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class ShareActivity extends Activity {

	private RestUtils restUtils = new RestUtils();
	
	private Button shareBtn;
	
	private Grave graveToShare;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		final AutoCompleteTextView typeAheadInput = (AutoCompleteTextView) findViewById(R.id.usersList);
		graveToShare = (Grave) getIntent().getSerializableExtra(DataActivity.GRAVE_BUNDLE);
		
		shareBtn = (Button) findViewById(R.id.confirmBtn);
		
		ArrayAdapter<String> adapter;
		List<String> usersLogins;
		try {
			usersLogins = restUtils.downloadLogins();

			 adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
					usersLogins);
			typeAheadInput.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		shareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String choosedLogin = typeAheadInput.getText().toString();
				
				Human human;
				try {
					human = restUtils.getUser(choosedLogin);
					graveToShare.getOwners().add(human);
					
					restUtils.updateGravesOwners(graveToShare);
					
					Toast.makeText(ShareActivity.this, "Udostępniono dane", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share, menu);
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
