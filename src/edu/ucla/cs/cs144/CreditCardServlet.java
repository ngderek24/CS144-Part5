package edu.ucla.cs.cs144;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreditCardServlet extends HttpServlet implements Servlet {

    public CreditCardServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String itemID = (String) session.getAttribute("itemID");
        String name = (String) session.getAttribute("name");
        String buyPrice = (String) session.getAttribute("buyPrice");

        request.setAttribute("itemID", itemID);
        request.setAttribute("name", name);
        request.setAttribute("buyPrice", buyPrice);

        String url = String.format("https://%s:8443%s/confirm", request.getServerName(), request.getContextPath());

        request.setAttribute("url", url);

        request.getRequestDispatcher("creditCardInputPage.jsp").forward(request, response);
    }
}
