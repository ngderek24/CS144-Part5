<%@ page import="edu.ucla.cs.cs144.XMLBean" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>Search Results</title>
</head>

<body>
<%
	XMLBean xmlBean = (XMLBean) request.getAttribute("xmlBean");
	out.println(xmlBean.getName());
	out.println(xmlBean.getCurrently());

	for (String category : xmlBean.getCategories()) {
		out.println(category);
	}

	out.println(xmlBean.getSellerRating());
	out.println(xmlBean.getSellerID());
	out.println(xmlBean.getLatitude());
	out.println(xmlBean.getLongitude());

	for (Map<String, String> bid : xmlBean.getBids()) {
		out.println(bid.get("bidderID"));
		out.println(bid.get("bidderRating"));
		out.println(bid.get("time"));
		out.println(bid.get("amount"));
		out.println(bid.get("bidderLocation"));
		out.println(bid.get("bidderCountry"));
	}
%>
</body>

</html>