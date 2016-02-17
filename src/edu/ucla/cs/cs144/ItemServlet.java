package edu.ucla.cs.cs144;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.*;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        String itemID = request.getParameter("itemid");
        String xmlData = AuctionSearchClient.getXMLDataForItemId(itemID);
        XMLBean xmlBean = processData(xmlData);

        request.setAttribute("xmlBean", xmlBean);
        request.getRequestDispatcher("item.jsp").forward(request, response);
        //out.println(name);
    }

    private static XMLBean processData(String xmlData) {
        Document doc = null;
        try {
            doc = loadXMLFromString(xmlData);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlData);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }

        XMLBean xmlBean = new XMLBean();
        Element item = doc.getDocumentElement();
        xmlBean.setItemID(getElementTextByTagNameNR(item, "ItemID"));
        xmlBean.setName(getElementTextByTagNameNR(item, "Name"));
        xmlBean.setCurrently(getElementTextByTagNameNR(item, "Currently"));
        xmlBean.setBuyPrice(filterNullString(getElementTextByTagNameNR(item, "Buy_Price")));
        xmlBean.setFirstBid(getElementTextByTagNameNR(item, "First_Bid"));
        xmlBean.setNumOfBids(getElementTextByTagNameNR(item, "Number_of_Bids"));
        xmlBean.setLocation(getElementTextByTagNameNR(item, "Location"));
        xmlBean.setCountry(getElementTextByTagNameNR(item, "Country"));
        xmlBean.setStarted(getElementTextByTagNameNR(item, "Started"));
        xmlBean.setEnds(getElementTextByTagNameNR(item, "Ends"));
        xmlBean.setDescription(getElementTextByTagNameNR(item, "Description"));
        return xmlBean;
    }

    private static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }

    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }

    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }

    private static String filterNullString(String s) {
        if (s.equals("")) {
            s = "\\N";
        }
        return s;
    }
}
