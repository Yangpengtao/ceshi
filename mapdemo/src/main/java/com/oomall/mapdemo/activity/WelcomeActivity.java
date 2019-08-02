package com.oomall.mapdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import com.ypt.love.wss.shain.R;

public class WelcomeActivity extends Activity {

	private TextView mTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mTv = (TextView) findViewById(R.id.tv);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.mipmap.ic_launcher);
	}
}
