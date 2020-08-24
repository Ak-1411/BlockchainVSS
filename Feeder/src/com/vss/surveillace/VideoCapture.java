package com.vss.surveillace;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class VideoCapture implements Runnable {

	boolean run = true;

	ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
	String filepath;

	public static boolean continueRunning = true;

	public VideoCapture(String filepath) {
		this.filepath = filepath;
		new Thread(this).start();
	}

	IplImage toIplImage(BufferedImage bufImage) {

		Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
		IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
		return iplImage;
	}

	public void run() {
		try {

			OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
			grabber.start();

			int i = 0;
			while (true) {
				i++;
				Frame frame = grabber.grab();
				new WriteThread(frame, i);

				if (!continueRunning) {
					break;
				}

			}
			grabber.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class WriteThread implements Runnable {
		Thread t;
		private Frame frame;
		private int i;

		WriteThread(Frame frame, int i) {
			this.frame = frame;
			this.i = i;
			t = new Thread(this);
			t.start();
		}

		@Override
		public void run() {
			try {
				BufferedImage bufferedImage = Java2DFrameUtils.toBufferedImage(frame);
				File file = new File(filepath + File.separator + "image" + i + ".png");
				ImageIO.write(bufferedImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
