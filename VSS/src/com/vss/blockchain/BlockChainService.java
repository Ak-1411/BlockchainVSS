package com.vss.blockchain;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vss.constants.Constants;

public class BlockChainService {

	public List<String> getAllFrames() throws Exception {
		List<String> result = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		BlockChain bc2 = new BlockChain();
		File folder = new File(Constants.BLOCKCHAIN_PATH);
		if (!folder.exists())
			return null;
		else {
			for (File f : folder.listFiles()) {
				bc2 = mapper.readValue(f, BlockChain.class);
				for (Block b : bc2.getBlockchain()) {
					String frame = b.getData();
					result.add(frame);
				}
			}
		}
		return result;

	}

	public List<String> getFrames(String filename) throws Exception {
		if (filename.equals("all"))
			return getAllFrames();
		
		List<String> result = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		BlockChain bc2 = new BlockChain();
		File folder = new File(Constants.BLOCKCHAIN_PATH);
		if (!folder.exists())
			return null;
		else {
			File f = new File(Constants.BLOCKCHAIN_PATH + File.separator + filename + ".json");
			bc2 = mapper.readValue(f, BlockChain.class);
			for (Block b : bc2.getBlockchain()) {
				String frame = b.getData();
				result.add(frame);
			}
		}
		return result;

	}

	public Map<String, String> getFootageDetails() throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		File folder = new File(Constants.BLOCKCHAIN_PATH);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy | kk:mm:ss");
		if (!folder.exists())
			return null;
		else {
			for (File f : folder.listFiles()) {
				Path path = Paths.get(f.getAbsolutePath());
				BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
				Timestamp ts = new Timestamp(attr.creationTime().toMillis());
				result.put(f.getName().substring(0, f.getName().indexOf(".")), sdf.format(ts));
			}
		}
		return result;

	}

}
