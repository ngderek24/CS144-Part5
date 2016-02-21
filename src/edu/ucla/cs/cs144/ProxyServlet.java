package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        String q = request.getParameter("q");

        if (q.equals(""))
            return;

        String url = "http://www.google.com/complete/search?output=toolbar&";
        String charset = "UTF-8";
        String query = String.format("q=%s", URLEncoder.encode(q, charset));
        URLConnection conn = new URL(url + query).openConnection();
        conn.setRequestProperty("Accept-Charset", charset);
        InputStream googleResponse = conn.getInputStream();

        java.util.Scanner s = new java.util.Scanner(googleResponse, charset).useDelimiter("\\A");
        String xmlData = s.hasNext() ? s.next() : "";

        int status = ((HttpURLConnection) conn).getResponseCode();
        response.setStatus(status);
        response.setContentType("text/xml");
        out.write(xmlData);
        out.flush();
        out.close();
    }
}
