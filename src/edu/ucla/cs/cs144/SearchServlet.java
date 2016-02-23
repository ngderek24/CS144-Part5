package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String q = request.getParameter("q");
        String numResultsToSkipString = request.getParameter("numResultsToSkip");
        String numResultsToReturnString = request.getParameter("numResultsToReturn");

        if (q != null && numResultsToSkipString != null && numResultsToReturnString != null &&
                isNumeric(numResultsToSkipString) && isNumeric(numResultsToReturnString)) {
            int numResultsToSkip = Integer.parseInt(numResultsToSkipString);
            int numResultsToReturn = Integer.parseInt(numResultsToReturnString);

            SearchResult[] searchResults = AuctionSearchClient.basicSearch(q, numResultsToSkip, numResultsToReturn);
            request.setAttribute("searchResults", searchResults);
            request.getRequestDispatcher("searchResults.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.html").forward(request, response);
        }
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
