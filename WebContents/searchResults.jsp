<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<html>
<head>
<title>Search Results</title>
</head>

<body>
<%
	SearchResult[] searchResults = (SearchResult[]) request.getAttribute("searchResults");
	for (SearchResult sr : searchResults) {
		String itemID = sr.getItemId();
		String itemLink = "<a href=\"item?itemid=" + itemID + "\">";
		out.println(itemLink + itemID + "</a>");
		out.println(sr.getName() + "<br>");
	}
%>
</body>

</html>