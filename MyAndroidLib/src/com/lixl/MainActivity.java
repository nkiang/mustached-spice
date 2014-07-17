package com.lixl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lixl.test.DialogsInPackage;
import com.lixl.test.TestListView;
import com.lixl.test.TestTextView;
import com.lixl.test.TestVideoBuffer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewsById();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void findViewsById(){
		Button video_buffer = (Button) this.findViewById(R.id.video_buffer);
		video_buffer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();
				Map<String, Object> temp = new HashMap<String, Object>();
				Intent intent = new Intent(MainActivity.this, TestVideoBuffer.class);
				temp.put("title", "VideoBuffer");
				temp.put("intent", intent);
				myData.add(temp);
				
				Map<String, Object> map = (Map<String, Object>) myData.get(0);//.getItemAtPosition(0);
				Intent intent2 = (Intent) map.get("intent");
				startActivity(intent2);
				
			}
		});
		Button test_text_view = (Button) this.findViewById(R.id.test_text_view);
		test_text_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TestTextView.class);
				startActivity(intent);
				
			}
		});
		Button test_dialog_view = (Button) this.findViewById(R.id.test_dialog_view);
		test_dialog_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DialogsInPackage.class);
				startActivity(intent);
			}
		});
		Button test_baseadaper_view = (Button) this.findViewById(R.id.test_base_adapter_view);
		test_baseadaper_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TestListView.class);
				startActivity(intent);
			}
		});
	}

}
