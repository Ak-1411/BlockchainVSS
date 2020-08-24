package com.vss.blockchain.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vss.blockchain.data.BlockChain;
import com.vss.blockchain.util.Constants;

public class BlockchainService {

	public List<BlockChain> constructblockchain() throws JsonParseException, JsonMappingException, IOException {
		List<BlockChain> result = new ArrayList<BlockChain>();
		File folder = new File(Constants.STORAGE_PATH);
		ObjectMapper mapper = new ObjectMapper();
		if (folder.exists()) {
			for (File file : folder.listFiles()) {
				BlockChain blockChain = new BlockChain();
				blockChain = mapper.readValue(file, BlockChain.class);
				result.add(blockChain);
			}
		}
		return result;
	}
}
