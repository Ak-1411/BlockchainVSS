package com.vss.dao;

import java.util.List;

import com.vss.model.Node;

public interface NodeDAO {

	public List<Node> getAllNodes() throws Exception;
}
