<%@page import="com.vss.blockchain.service.BlockchainService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vss.blockchain.data.Block"%>
<%@page import="com.vss.blockchain.data.BlockChain"%>
<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<script src='js/jquery.js' type='text/javascript'></script>
<script src='js/bootstrap.js' type='text/javascript'></script>
<link href='css/bootstrap.css' rel='stylesheet' type='text/css' />
<meta charset="BIG5">
<title>Blockchain Node</title>
</head>
<body>

	<%
	   BlockchainService bcService = new BlockchainService();
	   List<BlockChain> bcList = bcService.constructblockchain();
	   String nodename = request.getRequestURL().toString();
	   SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy | hh:mm");
	%>
	<table class='table' style='width: 100%; height: 100%;' border=1>
		<tbody>
			<tr>
				<td style='text-align: center;'><h1>
						Blockchain Node - <i><%=nodename%></i>
					</h1></td>
			</tr>

			<tr>
				<td>
					<div style='min-height: 500px;'>
						
						
					<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
						<%
							int b = 0;
							for (BlockChain blockchain: bcList)
							{
								b++;
								%>
								
								  <div class="panel panel-default">
								    <div class="panel-heading" role="tab" id="headingOne<%=b%>">
								      <h4 class="panel-title">
								        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne<%=b%>" aria-expanded="false" aria-controls="collapseOne<%=b%>">
								          Blockchain #<%=b %>				<span style='float: right;'>Blockchain Validation Check Passed: <b><%=blockchain.isChainValid() %></b></span>
								        </a>
								      </h4>
								    </div>
								    <div id="collapseOne<%=b%>" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne<%=b%>">
								      <div class="panel-body">
										<table class='table'>
				
											<tr>
												<th>Block #</th>
												<th>Hash</th>
												<th>Previous Hash</th>
												<th>Data</th>
												<th>Timestamp</th>
												<th>Nonce</th>
											</tr>
											<%
											   int counter = blockchain.getBlockchain().size();
											   List<Block> blockchaindata = blockchain.getBlockchain();
											   Collections.reverse(blockchaindata);
											   for (Block block : blockchaindata)
											   {
											%>
											<tr>
												<th><%=counter--%></th>
												<th><%=block.getHash()%></th>
												<th><%=block.getPreviousHash()%></th>
												<th><a href='#' data-toggle="modal" data-target="#<%=b %>data<%=counter+1%>">View</a>
																<!-- Modal -->
																<div class="modal fade" id="<%=b %>data<%=counter+1%>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
																  <div class="modal-dialog" role="document">
																    <div class="modal-content">
																      <div class="modal-header">
																        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
																        <h4 class="modal-title" id="myModalLabel">Data for Block #<%=counter+1%></h4>
																      </div>	
																      <div class="modal-body">
																        <textarea readonly="readonly" style='width: 100%; height: 500px;'><%=block.getData().toString() %></textarea>
																      </div>
																    </div>
																  </div>
																</div>
												
												
												</th>
												<th><%=sdf.format(block.getTimeStamp())%></th>
												<th><%=block.getNonce()%></th>
											</tr>
				
											<%
											   }
											   Collections.reverse(blockchaindata);
											%>
				
										</table>								
								      </div>
								    </div>
								  </div>
																
								
								<%
							}
						%>
						
						</div>


					</div>
				</td>
			</tr>

			<tr>
				<td style='text-align: center;'>&copy; 2019 Final Year Project
					Work.</td>
			</tr>
		</tbody>
	</table>
</body>
</html>