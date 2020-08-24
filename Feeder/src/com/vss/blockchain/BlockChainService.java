package com.vss.blockchain;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vss.constants.Constants;

public class BlockChainService {


	public void writeMultipleFrames(List<String> frames) throws Exception {

		int itr = 0;
		boolean lastItr = false;
		while (true) {
			if (lastItr)
				break;
			try {
				frames.get((itr * Constants.NUMBER_OF_FRAMES_PER_FILE) + Constants.NUMBER_OF_FRAMES_PER_FILE);
			} catch (IndexOutOfBoundsException e) {
				lastItr = true;
			}
			List<String> tempFrames = null;
			if (!lastItr)
				tempFrames = frames.subList(Constants.NUMBER_OF_FRAMES_PER_FILE * itr, (itr * Constants.NUMBER_OF_FRAMES_PER_FILE) + Constants.NUMBER_OF_FRAMES_PER_FILE);
			else
				tempFrames = frames.subList(Constants.NUMBER_OF_FRAMES_PER_FILE * itr, frames.size());

			ObjectMapper mapper = new ObjectMapper();
			BlockChain bc2 = new BlockChain();
			File folder = new File(Constants.BLOCKCHAIN_PATH);
			if (!folder.exists())
				folder.mkdirs();

			File[] data_files = folder.listFiles();
			Block block = null;
			String filename = null;
			if (data_files == null || data_files.length == 0) {
				filename = "1.json";
			} else {
				filename = (data_files.length + 1) + ".json";
			}
			block = new Block(tempFrames.get(0), null);
			block.mineBlock(1);

			String prevHash = block.hash;
			for (int i = 1; i < tempFrames.size(); i++) {
				block = new Block(tempFrames.get(i), prevHash);
				block.mineBlock(1);
				bc2.getBlockchain().add(block);
				prevHash = block.hash;
			}
			mapper.writeValue(new File(Constants.BLOCKCHAIN_PATH + File.separator + filename), bc2);
			itr++;
		}
	}

	public void distributeBlocks() {
		new LedgerDistributionThread();
	}


}
