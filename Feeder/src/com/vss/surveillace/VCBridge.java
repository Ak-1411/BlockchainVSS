package com.vss.surveillace;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.vss.blockchain.BlockChainService;

public class VCBridge implements Runnable {

	BlockChainService bc = new BlockChainService();
	String location;
	Thread t;

	public VCBridge(String location) {
		this.location = location;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		System.out.println("Running VC Bridge at " + location);
		while (true) {
			System.out.println("VCBridge Time Task Running");
			File folder = new File(location);
			File[] files = folder.listFiles();
			Arrays.sort(files, new Comparator<File>() {

				@Override
				public int compare(File f1, File f2) {
					String f1name = f1.getName();
					f1name = f1name.replace("image", "");
					f1name = f1name.replace(".png", "");

					String f2name = f2.getName();
					f2name = f2name.replace("image", "");
					f2name = f2name.replace(".png", "");

					int v1 = Integer.parseInt(f1name);
					int v2 = Integer.parseInt(f2name);
					if (v1 > v2)
						return 1;
					else if (v1 < v2)
						return -1;
					else
						return 0;
				}
			});

			List<String> frames = new ArrayList<>();
			for (File file : files) {

				String text;
				try {
					text = com.vss.surveillace.ImageIO.imageToString(file);
					System.out.println("Length: " + text.length());
					frames.add(text);
				} catch (Exception e) {
					e.printStackTrace();
				}
				file.delete();
			}
			if (frames.size() > 0) {
				try {
					bc.writeMultipleFrames(frames);
					bc.distributeBlocks();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(15000);
			} catch (Exception e) {

			}
		}
	}

}
