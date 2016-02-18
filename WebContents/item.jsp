<%@ page import="edu.ucla.cs.cs144.XMLBean" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>Search Results</title>
</head>

<body>
<%
	XMLBean xmlBean = (XMLBean) request.getAttribute("xmlBean");
	out.println("Item ID: " + xmlBean.getItemID() + "<br>");
	out.println("Name: " + xmlBean.getName() + "<br>");
	
	out.println("Categories: <ul>");
	for (String category : xmlBean.getCategories()) {
		out.println("<li>" + category + "</li>");
	}
	out.println("</ul>");
	
	out.println("Currently: " + xmlBean.getCurrently() + "<br>");
	out.println("Buy Price: " + xmlBean.getBuyPrice() + "<br>");
	out.println("First Bid: " + xmlBean.getFirstBid() + "<br>");
	out.println("Number of Bids: " + xmlBean.getNumOfBids() + "<br>");
	out.println("Item Location: " + xmlBean.getLocation() + "<br>");
	out.println("Latitude: " + xmlBean.getLatitude() + "<br>");
	out.println("Longitude: " + xmlBean.getLongitude() + "<br>");
	out.println("Item Country: " + xmlBean.getCountry() + "<br>");
	out.println("Started: " + xmlBean.getStarted() + "<br>");
	out.println("Ends: " + xmlBean.getEnds() + "<br>");
	out.println("Seller ID: " + xmlBean.getSellerID() + "<br>");
	out.println("Seller Rating: " + xmlBean.getSellerRating() + "<br>");

	out.println("Bids: <ul>");
	for (Map<String, String> bid : xmlBean.getBids()) {
		out.println("<li>");
		out.println("Bidder ID: " + bid.get("bidderID") + "<br>");
		out.println("Bidder Rating: " + bid.get("bidderRating") + "<br>");
		out.println("Time: " + bid.get("time") + "<br>");
		out.println("Amount: " + bid.get("amount") + "<br>");
		out.println("Bidder Location: " + bid.get("bidderLocation") + "<br>");
		out.println("Bidder Country: " + bid.get("bidderCountry") + "<br>");
		out.println("</li>");
	}
	out.println("</ul>");
%>
</body>

</html>