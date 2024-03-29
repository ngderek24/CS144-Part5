package edu.ucla.cs.cs144;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConfirmationServlet extends HttpServlet implements Servlet {

    public ConfirmationServlet() {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String itemID = (String) session.getAttribute("itemID");
        String name = (String) session.getAttribute("name");
        String buyPrice = (String) session.getAttribute("buyPrice");
        String creditCardNumber = (String) request.getParameter("creditCardNumber");

        request.setAttribute("itemID", itemID);
        request.setAttribute("name", name);
        request.setAttribute("buyPrice", buyPrice);
        request.setAttribute("creditCardNumber", creditCardNumber);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss aaa");
        String transactionTime = sdf.format(cal.getTime());
        request.setAttribute("transactionTime", transactionTime);

        request.getRequestDispatcher("confirmation.jsp").forward(request, response);
    }
}
