package com.motiondelta.MotionDelta;

import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;

// Handles camera initialization, frame grabbing, conversion to Mat, and running the capture loop
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
	
	// Starts the camera and sets the running state
	public void start() throws Exception {
		cameraHandle.start();
		runningState = true;
			
	}
	
	// Stops the camera and resets the running state
	public void stop() throws Exception {
		cameraHandle.stop();
		runningState = false;
	}
	
	// Grabs a single frame and validates that it contains image data
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
	
	// Converts a Frame to an OpenCv Mat and validates the result
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
	
	// Continuously captures frames, converts them, and performs motion detection
	public void runCaptureLoop() {
		MotionDetector detector = new MotionDetector();
		
		Mat previousImage = null;
		
		// Main capture loop
		while (isRunningState()) {
			
			// Grabbing a frame from the camera
			Frame currentFrame;
			try {
				currentFrame = grabFrame();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			// Converting frame to OpenCV Mat
			Mat currentImage;
			try {
				currentImage = convertToMat(currentFrame);	
			} catch(Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			// Initializing the first frame
			if (previousImage == null) {
				previousImage = currentImage;
				continue;
				
			} else {
				
				// Detecting motion between frames
				boolean motionDetected = detector.detectMotion(previousImage, currentImage);
				
				if (motionDetected) {
					System.out.println("Motion detected");
				} else {
					System.out.println("No motion.");
				}
				
				// Updating previousImage for the next iteration
				previousImage = currentImage;
			}
		
			
		}
		
		
	}
	
	
}
