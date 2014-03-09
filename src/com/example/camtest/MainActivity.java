package com.example.camtest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	int numberOfCameras;
	int defaultCameraId;
	//ImageView imgVieW;
	Camera mCamera;
	MenuItem SaveMnuItem;
    int cameraCurrentlyLocked;
    SurfaceHolder holder;
	SurfaceView view;
	RelativeLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		numberOfCameras = Camera.getNumberOfCameras();
		//imgVieW= (ImageView)findViewById(R.id.imageView1);
		view = (SurfaceView)findViewById(R.id.surfaceView1);
		holder= view.getHolder();
		mCamera = Camera.open();
		//holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				// Open the default i.e. the first rear facing camera.
		        //mCamera = Camera.open();
		        //cameraCurrentlyLocked = defaultCameraId;
		        //mCamera.setDisplayOrientation(90);
				try {
					Camera.Parameters params=null;
					params= mCamera.getParameters();
					params.setPreviewFormat(ImageFormat.NV21);
					mCamera.setParameters(params);
					mCamera.setPreviewDisplay(holder);
					mCamera.startPreview();
				    
	         	   	layout = (RelativeLayout)view.getParent();
	         	   	layout.getLayoutParams().width=params.getPreviewSize().width;
	         	   	layout.getLayoutParams().height=params.getPreviewSize().height;
				    
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
            
			}
		});
		// Find the ID of the default camera
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
        	Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
            	defaultCameraId = i;
            }
        }
    	try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }

	@Override
    protected void onResume() {
        super.onResume();
                //mPreview.setCamera(mCamera);
    }
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.exit:
			finish();
			System.exit(0);
		case R.id.take:
			if("Take Picture".equals(item.getTitle())){
				item.setTitle("Start Camera");
				mCamera.stopPreview();
				SaveMnuItem.setVisible(true);
				}
			else{
				item.setTitle("Take Picture");
				mCamera.startPreview();
				SaveMnuItem.setVisible(false);
			}
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		SaveMnuItem = menu.findItem(R.id.save);
		SaveMnuItem.setVisible(false);
		return true;
	}
}