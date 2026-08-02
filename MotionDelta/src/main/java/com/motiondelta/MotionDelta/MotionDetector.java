package com.motiondelta.MotionDelta;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.*;


public class MotionDetector {
	private Mat previousFrame; 
	private boolean isIntialized = false;
	private int motionThreshold;
	private int deltaThreshold;
	private int blurSize;
	private int dilationSize;
	private int lastMotionIntensity;
	private int lastMotionRegions;
	
	public MotionDetector(int motionThreshold, int deltaThreshold, int blurSize, int dilationSize) {
		// Storing configuration values
		this.motionThreshold = motionThreshold;
		this.deltaThreshold = deltaThreshold;
		this.blurSize = blurSize;
		this.dilationSize = dilationSize;
		
		// Initializing internal state
		this.previousFrame = null;
		this.isIntialized = false;
		this.lastMotionIntensity = 0;
		this.lastMotionRegions = 0;
	}
	
	public int getMotionThreshold() {
		return motionThreshold;
	}

	public void setMotionThreshold(int motionThreshold) {
		this.motionThreshold = motionThreshold;
	}

	public int getDeltaThreshold() {
		return deltaThreshold;
	}

	public void setDeltaThreshold(int deltaThreshold) {
		this.deltaThreshold = deltaThreshold;
	}

	public int getBlurSize() {
		return blurSize;
	}

	public void setBlurSize(int blurSize) {
		this.blurSize = blurSize;
	}

	public int getDilationSize() {
		return dilationSize;
	}

	public void setDilationSize(int dilationSize) {
		this.dilationSize = dilationSize;
	}

	public int getLastMotionIntensity() {
		return lastMotionIntensity;
	}

	public int getLastMotionRegions() {
		return lastMotionRegions;
	}

	// Detects motion by analyzing differences between two frames
	// Expects two OpenCV Mat Objects: a current and a previous frame
	// Determines motion using gray scale conversion, blurring, frame differencing,
	// thresholding, and counting significant pixel changes
	public boolean detectMotion(Mat previousImage, Mat currentImage) {
		
		// Converting both frames to grayscale
		Mat grayscalePrevious = new Mat();
		Mat grayscaleCurrent = new Mat();
		opencv_imgproc.cvtColor(previousImage, grayscalePrevious, COLOR_BGR2GRAY);
		opencv_imgproc.cvtColor(currentImage, grayscaleCurrent, COLOR_BGR2GRAY);
		
		// Reducing noise using Gaussian blur
		opencv_imgproc.GaussianBlur(grayscalePrevious, grayscalePrevious, new Size(blurSize, blurSize), 0);
		opencv_imgproc.GaussianBlur(grayscaleCurrent, grayscaleCurrent, new Size(blurSize, blurSize), 0);
		
		// Computing absolute difference between frames
		Mat pixelDifference = new Mat();
		opencv_core.absdiff(grayscalePrevious, grayscaleCurrent, pixelDifference);
		
		// Converting difference to grayscale for thresholding
		Mat grayscaleDifference = new Mat();
		opencv_imgproc.cvtColor(pixelDifference, grayscaleDifference, COLOR_BGR2GRAY);
		
		// Applying binary threshold to isolate areas of motion
		Mat thresholdedMask = new Mat();
		opencv_imgproc.threshold(grayscaleDifference, thresholdedMask, deltaThreshold, 255.0, THRESH_BINARY);
		
		// Strengthening areas of motion by dilating the mask
		Mat dilatedMask = new Mat();
		opencv_imgproc.dilate(
				thresholdedMask, 
				dilatedMask,
				opencv_imgproc.getStructuringElement(
						opencv_imgproc.MORPH_RECT,
						new Size(dilationSize, dilationSize)));
					
		// Counting white pixels to determine motion presence
		int whitePixelCount = opencv_core.countNonZero(dilatedMask);
		
		// Motion is detected if white pixel count exceeds threshold
		return whitePixelCount > motionThreshold;
		
	}


}


