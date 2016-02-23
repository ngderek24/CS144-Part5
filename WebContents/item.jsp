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
	String latitude = "";
	String longitude = "";
	String itemLocation = "";
	if (xmlBean != null) {
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
		itemLocation = xmlBean.getLocation();
		latitude = xmlBean.getLatitude();
		longitude = xmlBean.getLongitude();
		out.println("Item Location: " + itemLocation + "<br>");
		out.println("Latitude: " + latitude + "<br>");
		out.println("Longitude: " + longitude + "<br>");
		out.println("Item Country: " + xmlBean.getCountry() + "<br>");
		out.println("Started: " + xmlBean.getStarted() + "<br>");
		out.println("Ends: " + xmlBean.getEnds() + "<br>");
		out.println("Seller ID: " + xmlBean.getSellerID() + "<br>");
		out.println("Seller Rating: " + xmlBean.getSellerRating() + "<br>");
		out.println("Description: " + xmlBean.getDescription() + "<br>");

		List<Map<String, String>> bids = xmlBean.getBids();
		out.println("Bids: ");
		if (bids.size() == 0) {
			out.println("N/A");
		} else {
			out.println("<ul>");
			for (Map<String, String> bid : bids) {
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
		}
	} else {
		out.println("<h1>No match found</h1>");
		latitude = "37";
		longitude = "-120";
	}
%>
<div id="map_canvas"></div>
</body>

<script type="text/javascript"> 
	function initialize() { 
	  	var latitude = "<%= latitude %>";
		var longitude = "<%= longitude %>";
		if (latitude == "N/A" || longitude == "N/A") {
			geocodeAddress("<%= itemLocation %>");
		} else {
			console.log("lat long present");
			var latlng = new google.maps.LatLng(latitude, longitude); 
		    var myOptions = { 
		      	zoom: 10, // default is 8  
		      	center: latlng, 
		      	mapTypeId: google.maps.MapTypeId.ROADMAP 
			}; 
		    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions); 
		    var marker = new google.maps.Marker({
		        map: map,
		        position: latlng
			});
		}
	} 

	function geocodeAddress(itemLocation) {
  		geocoder = new google.maps.Geocoder();
  		geocoder.geocode({ 'address': itemLocation }, function (results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				var latlng = results[0].geometry.location;
		        var myOptions1 = { 
			      	zoom: 10, // default is 8  
			      	center: latlng, 
			      	mapTypeId: google.maps.MapTypeId.ROADMAP 
		    	}; 
			    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions1); 
		    	var marker = new google.maps.Marker({
		            map: map,
		            position: latlng
		    	});
			} else {
				var myOptions2 = { 
			      	zoom: 1, // default is 8  
			      	center: new google.maps.LatLng(37, -120), 
			      	mapTypeId: google.maps.MapTypeId.ROADMAP 
		    	}; 
		    	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions2); 
			}
		});
	}
</script> 

</html>