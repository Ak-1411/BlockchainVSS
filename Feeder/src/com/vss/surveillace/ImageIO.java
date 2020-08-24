package com.vss.surveillace;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageIO {

	public static String imageToString(File file) throws IOException {
		FileInputStream imageInFile = new FileInputStream(file);
		byte imageData[] = new byte[(int) file.length()];
		imageInFile.read(imageData);
		String imageDataString = encodeImage(imageData);
		imageInFile.close();
		return imageDataString;
	}

	public static String encodeImage(byte[] imageByteArray) {
		return Base64.getEncoder().encodeToString(imageByteArray);
	}

}
