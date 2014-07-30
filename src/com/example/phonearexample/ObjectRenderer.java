package com.example.phonearexample;

import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.methods.SpecularMethod;
import rajawali.math.vector.Vector3;
import android.content.Context;

import com.research.phonearlib.camera.TrackData;
import com.research.phonearlib.renderer.BaseRenderer;

public class ObjectRenderer extends BaseRenderer{
	private DirectionalLight mLight;
	private Object3D mObject;
	
	public ObjectRenderer(Context context, TrackData td) {
		super(context, td);
	}
	
	protected void initScene() {
		mLight = new DirectionalLight(0.0f, 1.0f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(1);

		getCurrentScene().addLight(mLight);

		try {
			/*LoaderMD5Mesh meshParser = new LoaderMD5Mesh(this,
					R.raw.boblampclean_mesh);
			meshParser.parse();

			LoaderMD5Anim animParser = new LoaderMD5Anim("attack2", this,
					R.raw.boblampclean_anim);
			animParser.parse();

			SkeletalAnimationSequence sequence = (SkeletalAnimationSequence) animParser
					.getParsedAnimationSequence();

			mObject = (SkeletalAnimationObject3D) meshParser
					.getParsedAnimationObject();*/
			
			GZIPInputStream gzi = new GZIPInputStream(mContext.getResources()
					.openRawResource(R.raw.android));
			ObjectInputStream fis = new ObjectInputStream(gzi);
			SerializedObject3D serializedObj = (SerializedObject3D) fis
					.readObject();
			fis.close();
			
			mObject = new Object3D(serializedObj);
			mObject.setScale(0.2);
			addChild(mObject);
			
			Material androidMaterial = new Material();
			androidMaterial.enableLighting(true);
			androidMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			androidMaterial.setSpecularMethod(new SpecularMethod.Phong());
			mObject.setColor(0x00dd00);
			mObject.setMaterial(androidMaterial);
			
			
			
			/*mObject.setAnimationSequence(sequence);
			mObject.setScale(.01f);
			
			mObject.play();		


			addChild(mObject);*/
			
			
			mObject.rotateAround(new Vector3(1.0, 0.0, 0.0), -90.f);
			mObject.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	protected void drawModel(boolean tracked){
		mObject.setVisible(false);
		
		if(tracked){			
			
			mObject.setVisible(true);
			
		}
	}

}