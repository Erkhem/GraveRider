package com.example.alwaysinmem.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.json.JSONException;

import com.example.alwaysinmem.model.Grave;
import com.google.gson.Gson;

import android.app.Activity;
import android.util.Log;

public class FileUtils {

	private static final String fileName = "grave1"; 
	
	private Gson gson = new Gson();
	
	public void saveFile(Grave grave, Activity activity) throws JSONException {

		String graveSerialized = gson.toJson(grave);
		
		try {
			StringBuilder stringBuilder = new StringBuilder();
			
			String actualContent = openFile(activity);
			actualContent = actualContent.replace("]", "");
			
			stringBuilder.append(actualContent);
			
			if (actualContent.equals("")) {
				stringBuilder.append("[");
			}
			
			stringBuilder.append(graveSerialized);
			stringBuilder.append(", ");
			stringBuilder.append("]");
			
			FileOutputStream fOut = activity.openFileOutput(fileName, activity.MODE_WORLD_WRITEABLE);
			fOut.write(stringBuilder.toString().getBytes());
			fOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String openFile(Activity activity) {
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
