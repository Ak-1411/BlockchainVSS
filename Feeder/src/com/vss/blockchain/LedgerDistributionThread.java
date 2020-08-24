package com.vss.blockchain;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vss.constants.Constants;
import com.vss.dao.NodeDAO;
import com.vss.daoimpl.NodeDAOImpl;
import com.vss.model.Node;

public class LedgerDistributionThread implements Runnable {

	Thread t;

	public LedgerDistributionThread() {
		t = new Thread(this);
		t.start();

	}

	public Map<Node, Integer> distributeTransactionDataToLedgers() throws Exception {

		NodeDAO nodeDAO = new NodeDAOImpl();
		Map<Node, Integer> result = new HashMap<>();
		for (Node node : nodeDAO.getAllNodes()) {
			try {
				// String url = "http://localhost:8080/Node/rest/operations/upload";
				String charset = "UTF-8";
				File folder = new File(Constants.BLOCKCHAIN_PATH);
				for (File textFile : folder.listFiles()) {
					String boundary = Long.toHexString(System.currentTimeMillis());
					String CRLF = "\r\n";
					String url = node.getUrl() + "/rest/operations/upload?filename="+textFile.getName();

					URLConnection connection = new URL(url).openConnection();
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
					try (OutputStream output = connection.getOutputStream();
							PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);) {
						writer.append("--" + boundary).append(CRLF);
						writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\""
								+ textFile.getName() + "\"").append(CRLF);
						writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
						writer.append(CRLF).flush();
						Files.copy(textFile.toPath(), output);
						output.flush();
						writer.append(CRLF).flush();

						writer.append("--" + boundary + "--").append(CRLF).flush();
					}
					result.put(node, ((HttpURLConnection) connection).getResponseCode());
				}
			} catch (Exception e) {
				System.out.println(node.getUrl() + " encountered a problem");
				e.printStackTrace();
			}

		}
		return result;

	}

	@Override
	public void run() {
		try {
			Map<Node, Integer> result = distributeTransactionDataToLedgers();
			Iterator<Node> it = result.keySet().iterator();
			while (it.hasNext()) {
				Node node = it.next();
				Integer ret = result.get(node);
				System.out.println(node.getUrl() + " : " + ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
