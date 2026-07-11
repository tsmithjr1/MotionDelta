package com.motiondelta.MotionDelta;

import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;

public class CameraIngestion {
	private OpenCVFrameGrabber cameraHandle;
	private int cameraIndex;
	private boolean runningState;
	
	public CameraIngestion(int cameraIndex) {
		this.cameraIndex = cameraIndex;
		this.cameraHandle = new OpenCVFrameGrabber(cameraIndex);
		this.runningState = false;
	}

	public OpenCVFrameGrabber getCameraHandle() {
		return cameraHandle;
	}

	public void setCameraHandle(OpenCVFrameGrabber cameraHandle) {
		this.cameraHandle = cameraHandle;
	}

	public int getCameraIndex() {
		return cameraIndex;
	}

	public void setCameraIndex(int cameraIndex) {
		this.cameraIndex = cameraIndex;
	}

	public boolean isRunningState() {
		return runningState;
	}

	public void setRunningState(boolean runningState) {
		this.runningState = runningState;
	}
	
	public void start() throws Exception {
		cameraHandle.start();
		runningState = true;
			
	}
	
	public void stop() throws Exception {
		cameraHandle.stop();
		runningState = false;
	}
	
	public Frame grabFrame() throws Exception {
		Frame singleFrame = cameraHandle.grabFrame();
		
		if (singleFrame == null) {
			throw new Exception("Camera returned a null frame");
		}
		
		if (singleFrame.image == null && singleFrame.samples == null) {
			throw new Exception("Frame contains no image data");
		}
		
		return singleFrame;
	}
	
	public Mat convertToMat(Frame frame) throws Exception {
		OpenCVFrameConverter.ToMat frameConverter = new OpenCVFrameConverter.ToMat();
		Mat mat = frameConverter.convertToMat(frame);
		
		if (mat == null) {
			throw new Exception("Frame cannot be converted to Mat");
		
		} 
		
		if (mat.empty()) {
			throw new Exception("Converted Mat is empty");
			
		}
		
		return mat;
	}
	
	public 
	
	
}
