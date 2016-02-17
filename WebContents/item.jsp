<%@ page import="edu.ucla.cs.cs144.XMLBean" %>
<html>
<head>
<title>Search Results</title>
</head>

<body>
<%
	XMLBean xmlBean = (XMLBean) request.getAttribute("xmlBean");
	out.println(xmlBean.getName());
	out.println(xmlBean.getCurrently());
%>
</body>

</html>