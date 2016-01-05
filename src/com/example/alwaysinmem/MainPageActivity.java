package com.example.alwaysinmem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainPageActivity extends Activity{

	private Button button;
	private String login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		
		Bundle extras = getIntent().getExtras();
		login = extras.getString(LoginActivity.CREDENDIALS);
		
		button = (Button) findViewById(R.id.button_new_item);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newItemIntent = new Intent(MainPageActivity.this, MainActivity.class);
				newItemIntent.putExtra(LoginActivity.CREDENDIALS, login);
				startActivity(newItemIntent);
				 Toast.makeText(getBaseContext(), "NEW ITEM", Toast.LENGTH_SHORT).show();				
			}
		});
		
		button = (Button) findViewById(R.id.button_list_of_item);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent dataIntent = new Intent(MainPageActivity.this, DataActivity.class);
				startActivity(dataIntent);
				 Toast.makeText(getBaseContext(), "Data list", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		button = (Button) findViewById(R.id.button_about_faq);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Toast.makeText(getBaseContext(), "FAQ clicked", Toast.LENGTH_SHORT).show();
			}
		});
	}	

}
