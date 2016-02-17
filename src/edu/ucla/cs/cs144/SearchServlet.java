package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        out.println("<html><body>request received by search servlet!</body></html>");
        String q = request.getParameter("q");
        String numResultsToSkip = request.getParameter("numResultsToSkip");
        String numResultsToReturn = request.getParameter("numResultsToReturn");
        out.println("q: " + q);
        out.println("numResultsToSkip: " + numResultsToSkip);
        out.println("numResultsToReturn: " + numResultsToReturn);
    }
}
