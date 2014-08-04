package com.example.phonearexample;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.research.phonearlib.NativeTracking;
import com.research.phonearlib.PhoneARActivity;
import com.research.phonearlib.camera.CameraFrame;
import com.research.phonearlib.camera.CvFrame.FrameAvailableListener;


public class MainActivity extends PhoneARActivity implements Type{
	private static final int			DISPLAY = 0;
	private static final int			TRAIN = 1;
	private static final int			TRACK = 2;
	

	
	private MenuItem			mItemTrain;
	private MenuItem			mItemSelectModel;
	private int					mMode = DISPLAY;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mCvFrame.resigsterFrameAvailableListener(new FrameAvailableListener(){

			@Override
			public void OnFrameAvailable(CameraFrame frame) {
				Mat resizeImage = new Mat(360, 640, CvType.CV_8UC1);
				Imgproc.resize(frame.gray(), resizeImage, resizeImage.size());
				
				switch(mMode){
				case TRAIN:
					NativeTracking.train(resizeImage.getNativeObjAddr());
					mMode = TRACK;
					break;
				case TRACK:
					float[] trans = new float[3];
					float[] rots = new float[4];
					boolean tracked = NativeTracking.track(resizeImage.getNativeObjAddr(), trans, rots);
					if(tracked){
						mTrackData.updateRots(rots);
						mTrackData.updateTrans(trans);
					}
					mTrackData.setTracked(tracked);
					break;
				default:
					mMode = DISPLAY;
					break;
						
				}								
			}
			
		});
		
		Bundle b = getIntent().getExtras();
		int type = -1;
		String result = "";
		if(b != null){
			type = b.getInt("type");
			result = b.getString("text");
		}
		switch(type){
		case TYPE_TEXT:
			mRenderer = new TextRenderer(this, mTrackData, result);
			break;
		case TYPE_IMAGE:
			mRenderer = new ImageRenderer(this, mTrackData);
			break;
		case TYPE_VIDEO:
			mRenderer = new VideoRenderer(this, mTrackData);
			break;
		case TYPE_MODEL:
			mRenderer = new ObjectRenderer(this, mTrackData);
			break;
		default:
			mRenderer = new ImageRenderer(this, mTrackData);
			break;
		}

		mRenderer.registerRendererStartListener(mCvFrame);
		mRenderer.setSurfaceView(mSurfaceView);
		
		setRenderer(mRenderer);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		mItemTrain = menu.add("Train");
		mItemSelectModel= menu.add("Select Model");
		
		return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){
		if(item == mItemTrain) {
			mMode = TRAIN;
		}else if(item == mItemSelectModel){
			Intent mIntent = new Intent(this, SelectActivity.class);
			startActivity(mIntent);
			finish();
		}
		
		return true;
	}
}
