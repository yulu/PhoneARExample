package com.example.phonearexample;

import java.io.IOException;

import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.VideoTexture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Plane;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.research.phonearlib.camera.TrackData;
import com.research.phonearlib.renderer.BaseRenderer;

public class VideoRenderer extends BaseRenderer{
	private MediaPlayer mMediaPlayer;
	private VideoTexture mVideoTexture;
	private Object3D mPlane;
	private boolean firstTrack = true;
	
	private String url = "http://www.pocketjourney.com/downloads/pj/video/famous.3gp";
	
	public VideoRenderer(Context context, TrackData td) {
		super(context, td);
		
		//load media
		//mMediaPlayer = MediaPlayer.create(getContext(),
		//		R.raw.test);

		
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setLooping(true);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mMediaPlayer.setDataSource(url);
			mMediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
	
	protected void initScene(){


		//create video texture and set material
		mVideoTexture = new VideoTexture("sintelTrailer", mMediaPlayer);
		Material material = new Material();
		material.setColorInfluence(0);
		try {
			material.addTexture(mVideoTexture);
		} catch (TextureException e) {
			e.printStackTrace();
		}

		//set up the plane
		mPlane = new Plane(3, 2, 1, 1);
		mPlane.setScale(0.5);
		mPlane.setMaterial(material);
		mPlane.rotateAround(new Vector3(0,1,0), 180.0);
		mPlane.setDoubleSided(true);
		addChild(mPlane);
	}

	@Override
	protected void drawModel(boolean tracked) {
		mPlane.setVisible(false);
		mVideoTexture.update();
		
		if(firstTrack){
			mMediaPlayer.start();
			firstTrack = false;
		}
		
		if(tracked){						
			mPlane.setVisible(true);			
		}
		
	}

}
