package com.example.phonearexample;


import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.research.phonearlib.camera.TrackData;
import com.research.phonearlib.renderer.BaseRenderer;

public class ImageRenderer extends BaseRenderer{

	private Object3D mPlane;
	private Material mMaterial;
	
	public ImageRenderer(Context context, TrackData td) {
		super(context, td);
	}
	
	protected void initScene() {
		//set material
		mMaterial = new Material();
		try {
			mMaterial.addTexture(LoadImage());
			mMaterial.setColorInfluence(0);
		} catch (TextureException e) {
			e.printStackTrace();
		}
		
		mPlane = new Plane(1, 1, 1, 1);
		mPlane.setDoubleSided(true);
		mPlane.setMaterial(mMaterial);
		
		getCurrentScene().addChildAt(mPlane, 0);
	}
	

	@Override
	protected void drawModel(boolean tracked) {
		mPlane.setVisible(false);
		
		if(tracked){						
			mPlane.setVisible(true);			
		}
		
	}


	//TODO: load image from local path
	private ATexture LoadImage(){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		
		int resourceId = mContext.getResources().getIdentifier(
				"ic_launcher", "drawable",
				"com.example.phonearexample");

		Bitmap bitmap = BitmapFactory.decodeResource(
				mContext.getResources(), resourceId, options);

		ATexture texture = new Texture("image", bitmap);
		texture.setMipmap(true);
		texture.shouldRecycle(true);
		
		return mTextureManager.addTexture(texture);
	}

}
