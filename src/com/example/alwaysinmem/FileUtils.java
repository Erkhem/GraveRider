package com.example.alwaysinmem;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class FileUtils {

	private static final String fileName = "grave1"; 
	
	private Gson gson = new Gson();
	
	public void saveFile(Grave grave, Activity activity) throws JSONException {

		String graveSerialized = gson.toJson(grave);
		
		try {
			StringBuilder stringBuilder = new StringBuilder();
			
			String actualContent = openFile(fileName, activity);
			actualContent = actualContent.replace("]", "");
			
			stringBuilder.append(actualContent);
			
			if (actualContent.equals("")) {
				stringBuilder.append("[");
			}
			
			stringBuilder.append(graveSerialized);
			stringBuilder.append(", ");
			stringBuilder.append("]");
			
			FileOutputStream fOut = activity.openFileOutput(fileName, activity.MODE_WORLD_READABLE);
			fOut.write(stringBuilder.toString().getBytes());
			fOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String openFile(String fileName, Activity activity) {
		try {
			FileInputStream fin = activity.openFileInput(fileName);
			int c;
			String temp = "";

			while ((c = fin.read()) != -1) {
				temp = temp + Character.toString((char) c);
			}

			return temp;
		} catch (Exception e) {
			Log.e("ERROR", "Blad podczas otwierania pliku");
		}
		return null;
	}

}
