package com.oomall.mapdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.oomall.mapdemo.camera.CameraManager;
import com.oomall.mapdemo.camera.interf.OnCameraStatusListener;
import com.oomall.mapdemo.util.ImageCache;
import com.oomall.mapdemo.util.ImageUtil;
import com.oomall.mapdemo.util.Tools;
import com.ypt.love.wss.shain.R;

import java.io.IOException;

public class CaptureActivity extends Activity implements
        SurfaceHolder.Callback, OnCameraStatusListener {

    private CameraManager cameraManager;

    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        Tools.setStateBar(findViewById(R.id.fff), this);
        cameraManager = new CameraManager(this);
        cameraManager.setOnCameraStatusListener(this);
    }

    public void takepicture(View v) {
        cameraManager.takePhoto();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageCache.clear();
        mSurfaceView.post(new Runnable() {

            @Override
            public void run() {
                try {
                    cameraManager.setManualCameraId(cameraManager.FRONT_CAMERA);
                    cameraManager.openDriver(mSurfaceView.getHolder());
                    cameraManager.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraManager.stopPreview();
        cameraManager.closeDriver();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {

    }

    @Override
    public void onCameraStopped(byte[] data) {
        Bitmap btm = ImageUtil.reverseBitmap(ImageUtil.rotateBitmap(data, -90),
                0);

        ImageCache.put("curr_img", btm);

        Log.e("aaaa", "--------btm--------" + btm);
        // // ϵͳʱ��
        // long dateTaken = System.currentTimeMillis();
        // String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken)
        // .toString() + ".jpg";
        // final String tempPath = Environment.getExternalStorageDirectory()
        // .getPath() + "/" + filename;
        // Log.e("aaaaa", "---" + tempPath);
        // FileOutputStream fos = null;
        // try {
        // fos = new FileOutputStream(tempPath);
        // fos.write(data);
        // fos.flush();
        // // �����ҵĲü�����
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // try {
        // if (fos != null) {
        // fos.close();
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }

        Intent intent = new Intent(CaptureActivity.this, SetImgActivity.class);
        startActivity(intent);

    }

    @Override
    public void onAutoFocus(boolean success) {
        Log.e("aaaaa", "--------onAutoFocus------" + success);
    }

}
