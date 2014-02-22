package com.example.camtest;

import java.io.IOException;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {
	int numberOfCameras;
	int defaultCameraId;;
	Camera mCamera;
    int cameraCurrentlyLocked;
    SurfaceHolder holder;
	SurfaceView view;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		numberOfCameras = Camera.getNumberOfCameras();
		view = (SurfaceView)findViewById(R.id.surfaceView1);
		holder= view.getHolder();
		mCamera = Camera.open();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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
		        try {
					mCamera.setPreviewDisplay(holder);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
                mCamera.startPreview();
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
