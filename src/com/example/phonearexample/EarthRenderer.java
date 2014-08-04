package com.example.phonearexample;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Sphere;
import android.content.Context;

import com.research.phonearlib.camera.TrackData;
import com.research.phonearlib.renderer.BaseRenderer;

public class EarthRenderer extends BaseRenderer{
	private Object3D mSphere;
	
	public EarthRenderer(Context context, TrackData td) {
		super(context, td);
	}
	
	protected void initScene(){
		DirectionalLight mLight = new DirectionalLight(1f, 0.2f, -1.0f);
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);
		
		getCurrentScene().addLight(mLight);
		
		try{
			Material material = new Material();
			material.addTexture(new Texture("earthColors", R.drawable.world_map_1));
			material.setColorInfluence(0);
			mSphere = new Sphere(1, 24, 24);
			mSphere.setScale(0.5);
			mSphere.setMaterial(material);
			getCurrentScene().addChild(mSphere);
		}catch(TextureException e){
			e.printStackTrace();
		}
		
	}

	@Override
	protected void drawModel(boolean tracked) {
		mSphere.setVisible(false);
		
		
		if(tracked){						
			mSphere.setVisible(true);			
		}
		mSphere.setRotY(mSphere.getRotY() + 1);
	}

}
