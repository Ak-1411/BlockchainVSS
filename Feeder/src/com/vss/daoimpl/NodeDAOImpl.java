package com.vss.daoimpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.vss.dao.NodeDAO;
import com.vss.model.Node;
import com.vss.util.DBConnection;

public class NodeDAOImpl implements NodeDAO {

	@Override
	public List<Node> getAllNodes() throws Exception {
		Connection con = null;
		List<Node> result = null;
		try {
			con = DBConnection.connect();
			ResultSet rs = con.createStatement().executeQuery("select * from node");
			result = new ArrayList<>();
			while (rs.next()) {
				Node node = new Node();
				node.setName(rs.getString("name"));
				node.setUrl(rs.getString("url"));
				result.add(node);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			con.close();
		}
		return result;
	}
}
