package com.motiondelta.MotionDelta;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.*;


public class MotionDetector {
	// Detects motion by analyzing differences between two frames
	// Expects two OpenCV Mat Objects: a current and a previous frame
	// Determines motion using gray scale conversion, blurring, frame differencing,
	// thresholding, and counting significant pixel changes

	public boolean detectMotion(Mat previousImage, Mat currentImage) {
		Mat pixelDifference = new Mat();
		
		// Reducing noise in both frames
		opencv_imgproc.GaussianBlur(previousImage, previousImage, new Size(21, 21), 0);
		opencv_imgproc.GaussianBlur(currentImage, currentImage, new Size(21, 21), 0);
		
		// Computing absolute difference between frames
		opencv_core.absdiff(previousImage, currentImage, pixelDifference);
		
		Mat grayscaleDifference = new Mat();
		
		// Converting difference to grayscale
		opencv_imgproc.cvtColor(pixelDifference, grayscaleDifference, COLOR_BGR2GRAY);
		
		Mat thresholdedMask = new Mat();
		
		// Applying binary threshold to isolate areas of motion 
		opencv_imgproc.threshold(grayscaleDifference, thresholdedMask, 25.0, 255.0, THRESH_BINARY);
		
		Mat dilatedMask = new Mat();
		
		// Strengthening areas of motion by dilating the mask
		opencv_imgproc.dilate(thresholdedMask, dilatedMask,
				opencv_imgproc.getStructuringElement(opencv_imgproc.MORPH_RECT, new Size (3, 3)));
		
		// Counting white pixels to determine motion presence
		int whitePixelCount = opencv_core.countNonZero(dilatedMask);
		
		return whitePixelCount > 0;
		
	}


}


