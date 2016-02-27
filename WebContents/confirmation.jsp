<html>
<head>
<title>Confirmation Page</title>
</head>
<body>
<%
    out.println("Item ID: " + (String) request.getAttribute("itemID") + "<br>");
    out.println("Item Name: " + (String) request.getAttribute("name") + "<br>");
    out.println("Buy Price: " + (String) request.getAttribute("buyPrice") + "<br>");
    out.println("Credit Card Number: " + (String) request.getAttribute("creditCardNumber") + "<br>");
    out.println("Transaction Time: " + (String) request.getAttribute("transactionTime") + "<br>");
%>
</body>
</html>
