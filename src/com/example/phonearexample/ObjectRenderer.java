package com.example.phonearexample;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.methods.SpecularMethod;
import rajawali.math.vector.Vector3;
import rajawali.parser.LoaderOBJ;
import rajawali.parser.ParsingException;
import android.content.Context;
import android.graphics.Color;

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
			
			/*GZIPInputStream gzi = new GZIPInputStream(mContext.getResources()
					.openRawResource(R.raw.android));
			ObjectInputStream fis = new ObjectInputStream(gzi);
			SerializedObject3D serializedObj = (SerializedObject3D) fis
					.readObject();
			fis.close();*/
			
			/*ObjectInputStream ois = new ObjectInputStream(mContext
					.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois
					.readObject();
			ois.close();*/
			
			LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(),
					mTextureManager, R.raw.teapot_obj);
			try {
				objParser.parse();

			} catch (ParsingException e) {
				e.printStackTrace();
			}
			
			//mObject = serializedObj.getParsedObject();
			//mObject = new Object3D(serializedObj);
			mObject = objParser.getParsedObject();
			mObject.setScale(0.2);
			mObject.setDoubleSided(true);

			
			Material androidMaterial = new Material();
			androidMaterial.enableLighting(true);
			androidMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			androidMaterial.setSpecularMethod(new SpecularMethod.Phong(Color.WHITE, 90));
			mObject.setColor(0x00ff00);
			mObject.setMaterial(androidMaterial);
			
			
			mObject.rotateAround(new Vector3(1.0, 0.0, 0.0), -90.f);
			//mObject.rotateAround(new Vector3(0.0, 0.0, 1.0), -180.f);
			mObject.setVisible(false);
			
			addChild(mObject);
			
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