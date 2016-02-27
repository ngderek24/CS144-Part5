<html>
<head>
<title>Credit Card Input Page</title>
</head>
<body>
<%
    out.println("Item ID: " + (String) request.getAttribute("itemID") + "<br>");
    out.println("Item Name: " + (String) request.getAttribute("name") + "<br>");
    out.println("Buy Price: " + (String) request.getAttribute("buyPrice") + "<br>");
%>

<form action=<%=(String) request.getAttribute("url")%> method='POST'>
    <input type="text" name="creditCardNumber"></input>
    <input type="submit" value="Submit"></input>
</form>
</body>
</html>
