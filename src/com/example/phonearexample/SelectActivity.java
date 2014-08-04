package com.example.phonearexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;


public class SelectActivity extends Activity implements Type{
	//content button
	private LinearLayout mTextButton;
	private LinearLayout mImageButton;
	private LinearLayout mVideoButton;
	private LinearLayout mLoadModelButton;
	

	@Override
	public void onCreate(Bundle savedInstanceStat){
		super.onCreate(savedInstanceStat);
		
		setContentView(R.layout.activity_main);
		mTextButton = (LinearLayout)findViewById(R.id.text_button);
		mImageButton = (LinearLayout)findViewById(R.id.image_button);
		mVideoButton = (LinearLayout)findViewById(R.id.video_button);
		mLoadModelButton = (LinearLayout)findViewById(R.id.model_button);
		
		SetButtons();
		
	}
	
	private void SetButtons(){
		mTextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SetBuilder(TYPE_TEXT);
			}
				
		});
		
		mImageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SetBuilder(TYPE_IMAGE);
			}
				
		});
		
		mVideoButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SetBuilder(TYPE_VIDEO);
			}
				
		});
		
		mLoadModelButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SetBuilder(TYPE_MODEL);
			}
				
		});	
		
	}
	
	private void SetBuilder(final int type){
		final EditText editText = new EditText(this);
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Input the text or path to the image/video/model file")
			.setView(editText);
		
		builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				final String result = editText.getText().toString();
				
				Intent i = new Intent(SelectActivity.this, MainActivity.class);
				Bundle para = new Bundle();
				para.putInt("type", type);
				para.putString("text", result);
				i.putExtras(para);
				startActivity(i);
				finish();
				
			}
		});
		
		builder.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//do nothing
				
			}
		});
		
		builder.create().show();
		
	}

}
