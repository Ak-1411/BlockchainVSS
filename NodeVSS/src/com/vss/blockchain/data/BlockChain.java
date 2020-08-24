package com.vss.blockchain.data;

import java.util.ArrayList;

public class BlockChain
{

	private ArrayList<Block> blockchain = new ArrayList<Block>();
	private int difficulty = 1;
	private boolean chainValid;

	public ArrayList<Block> getBlockchain()
	{
		return blockchain;
	}

	public Boolean isChainValid()
	{
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		for (int i = 1; i < blockchain.size(); i++)
		{
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			if (!currentBlock.getHash().equals(currentBlock.calculateHash()))
			{
				System.out.println("Current Hashes not equal");
				return false;
			}

			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash()))
			{
				System.out.println("Previous Hashes not equal");
				return false;
			}

			if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget))
			{
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		System.out.println("The blockchain is Valid");
		return true;
	}
}