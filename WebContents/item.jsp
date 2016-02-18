<%@ page import="edu.ucla.cs.cs144.XMLBean" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>Search Results</title>
<script type="text/javascript" 
    src="http://maps.google.com/maps/api/js?sensor=false"> 
</script> 
<style>
	#map_canvas {
		width: 500px;
		height: 400px;
	}
</style>
</head>

<body onload="initialize()">
	<form action="item">
	  	ItemID: <input type="text" name="itemid">
	  	<input type="submit" value="Submit">
	</form>
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
	String latitude = xmlBean.getLatitude();
	String longitude = xmlBean.getLongitude();
	out.println("Latitude: " + latitude + "<br>");
	out.println("Longitude: " + longitude + "<br>");
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
<div id="map_canvas"></div>
</body>

<script type="text/javascript"> 
  function initialize() { 
  	var latitude = <%= latitude %>;
  	var longitude = <%= longitude %>;
    var latlng = new google.maps.LatLng(latitude, longitude); 
    var myOptions = { 
      zoom: 10, // default is 8  
      center: latlng, 
      mapTypeId: google.maps.MapTypeId.ROADMAP 
    }; 
    var map = new google.maps.Map(document.getElementById("map_canvas"), 
        myOptions); 
  } 
</script> 

</html>