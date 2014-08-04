package com.example.phonearexample;


import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
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
		
		mPlane = new Plane(3, 2, 1, 1);
		mPlane.setScale(0.5);
		//mPlane.setDoubleSided(true);
		mPlane.rotateAround(new Vector3(0,1,0), 180.0);
		mPlane.setMaterial(mMaterial);
		
		getCurrentScene().addChildAt(mPlane, 0);
	}
	

	@Override
	protected synchronized void drawModel(boolean tracked) {
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
				"map", "drawable",
				"com.example.phonearexample");

		Bitmap bitmap = BitmapFactory.decodeResource(
				mContext.getResources(), resourceId, options);

		ATexture texture = new Texture("image", bitmap);
		texture.setMipmap(true);
		texture.shouldRecycle(true);
		
		return mTextureManager.addTexture(texture);
	}

}
