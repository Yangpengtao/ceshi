package com.oomall.mapdemo.camera.view;

import java.io.IOException;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {

	/** LOG��ʶ */
	// private static final String TAG = "CameraPreview";

	/** �ֱ��� */
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;

	/** �����ӿ� */
	private OnCameraStatusListener listener;

	private SurfaceHolder holder;
	private Camera camera;

	// ����һ��PictureCallback���󣬲�ʵ�����е�onPictureTaken����
	private PictureCallback pictureCallback = new PictureCallback() {

		// �÷������ڴ�����������Ƭ����
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			// ֹͣ��Ƭ����
			camera.stopPreview();
			camera = null;

			// ���ý����¼�
			if (null != listener) {
				listener.onCameraStopped(data);
			}
		}
	};

	// Preview��Ĺ��췽��
	public CameraPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// ���SurfaceHolder����
		holder = getHolder();
		// ָ�����ڲ�׽�����¼���SurfaceHolder.Callback����
		holder.addCallback(this);
		// ����SurfaceHolder���������
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// ��surface����ʱ����
	public void surfaceCreated(SurfaceHolder holder) {
		// Log.e(TAG, "==surfaceCreated==");
		// ���Camera����
		camera = Camera.open();
		try {
			// ����������ʾ���������SurfaceHolder����
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
			// �ͷ��ֻ�����ͷ
			camera.release();
			camera = null;
		}
	}

	// ��surface����ʱ����
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Log.e(TAG, "==surfaceDestroyed==");
		// �ͷ��ֻ�����ͷ
		camera.release();
	}

	// ��surface�Ĵ�С�����ı�ʱ����
	public void surfaceChanged(final SurfaceHolder holder, int format, int w,
			int h) {
		// Log.e(TAG, "==surfaceChanged==");
		try {
			// ��ȡ���������
			Camera.Parameters parameters = camera.getParameters();
			// ������Ƭ��ʽ
			parameters.setPictureFormat(PixelFormat.JPEG);
			// ����Ԥ䯳ߴ�
			parameters.setPreviewSize(WIDTH, HEIGHT);
			// ������Ƭ�ֱ���
			parameters.setPictureSize(WIDTH, HEIGHT);
			// �������������
			camera.setParameters(parameters);
			// ��ʼ����
			camera.startPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ֹͣ���գ������������Ƭ����PictureCallback�ӿڵ�onPictureTaken����
	public void takePicture() {
		// Log.e(TAG, "==takePicture==");
		if (camera != null) {
			// �Զ��Խ�
			camera.autoFocus(new AutoFocusCallback() {
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					if (null != listener) {
						listener.onAutoFocus(success);
					}
					// �Զ��Խ��ɹ��������
					if (success) {
						camera.takePicture(null, null, pictureCallback);
					}
				}
			});
		}
	}

	// ���ü����¼�
	public void setOnCameraStatusListener(OnCameraStatusListener listener) {
		this.listener = listener;
	}

	/**
	 * ������ռ����ӿ�
	 */
	public interface OnCameraStatusListener {

		// ������ս����¼�
		void onCameraStopped(byte[] data);

		// ����ʱ�Զ��Խ��¼�
		void onAutoFocus(boolean success);
	}

}