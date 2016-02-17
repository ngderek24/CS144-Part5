<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<html>
<head>
<title>Search Results</title>
</head>

<body>
<%
	SearchResult[] searchResults = (SearchResult[]) request.getAttribute("searchResults");
	for (SearchResult sr : searchResults) {
		out.println(sr.getItemId());
	}
%>
</body>

</html>