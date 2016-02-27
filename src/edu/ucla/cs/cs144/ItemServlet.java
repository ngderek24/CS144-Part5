package edu.ucla.cs.cs144;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemID = request.getParameter("itemid");

        if (itemID != null && isNumeric(itemID) && itemID.length() == 10) {
            String xmlData = AuctionSearchClient.getXMLDataForItemId(itemID);
            XMLBean xmlBean = null;
            try {
                xmlBean = processData(xmlData);
            } catch (Exception e){
                //xmlBean = null;
            }
            request.setAttribute("xmlBean", xmlBean);

            HttpSession session = request.getSession(true);
            session.setAttribute("itemID", xmlBean.getItemID());
            session.setAttribute("name", xmlBean.getName());
            session.setAttribute("buyPrice", xmlBean.getBuyPrice());

            request.getRequestDispatcher("item.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.html").forward(request, response);
        }
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static XMLBean processData(String xmlData) throws Exception {
        Document doc = null;
        //try {
            doc = loadXMLFromString(xmlData);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.exit(3);
//        }
//        catch (SAXException e) {
//            System.out.println("Parsing error on file " + xmlData);
//            System.out.println("  (not supposed to happen with supplied XML files)");
//            e.printStackTrace();
//            System.exit(3);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            System.exit(3);
//        }

        XMLBean xmlBean = new XMLBean();
        Element item = doc.getDocumentElement();
        xmlBean.setItemID(item.getAttribute("ItemID"));
        xmlBean.setName(getElementTextByTagNameNR(item, "Name"));
        xmlBean.setCurrently(getElementTextByTagNameNR(item, "Currently"));
        xmlBean.setBuyPrice(filterNullString(getElementTextByTagNameNR(item, "Buy_Price")));
        xmlBean.setFirstBid(getElementTextByTagNameNR(item, "First_Bid"));
        xmlBean.setNumOfBids(getElementTextByTagNameNR(item, "Number_of_Bids"));

        Element locationElement = getElementByTagNameNR(item, "Location");
        xmlBean.setLatitude(filterNullString(locationElement.getAttribute("Latitude")));
        xmlBean.setLongitude(filterNullString(locationElement.getAttribute("Longitude")));
        xmlBean.setLocation(getElementTextByTagNameNR(item, "Location"));
        xmlBean.setCountry(getElementTextByTagNameNR(item, "Country"));
        xmlBean.setStarted(getElementTextByTagNameNR(item, "Started"));
        xmlBean.setEnds(getElementTextByTagNameNR(item, "Ends"));
        xmlBean.setDescription(getElementTextByTagNameNR(item, "Description"));

        Element sellerElement = getElementByTagNameNR(item, "Seller");
        xmlBean.setSellerRating(sellerElement.getAttribute("Rating"));
        xmlBean.setSellerID(sellerElement.getAttribute("UserID"));

        List<String> categories = new ArrayList<String>();
        for (Element category : getElementsByTagNameNR(item, "Category")) {
            categories.add(getElementText(category));
        }
        xmlBean.setCategories(categories);

        xmlBean.setBids(getBidsList(item));

        return xmlBean;
    }

    private static List<Map<String, String>> getBidsList(Element item) {
        Element bidsElement = getElementByTagNameNR(item, "Bids");
        Element[] bidElements = getElementsByTagNameNR(bidsElement, "Bid");
        Map<Date, Map<String, String>> dateToBidMap = new TreeMap<>(Collections.reverseOrder());
        for (Element bid : bidElements) {
            Element bidder = getElementByTagNameNR(bid, "Bidder");
            String bidderRating = bidder.getAttribute("Rating");
            String bidderID = bidder.getAttribute("UserID");
            String bidderLocation = filterNullString(getElementTextByTagNameNR(bidder, "Location"));
            String bidderCountry = filterNullString(getElementTextByTagNameNR(bidder, "Country"));
            String time = getElementTextByTagNameNR(bid, "Time");
            String amount = getElementTextByTagNameNR(bid, "Amount");

            Map<String, String> bidMap = new HashMap<String, String>();
            bidMap.put("bidderRating", bidderRating);
            bidMap.put("bidderID", bidderID);
            bidMap.put("bidderLocation", bidderLocation);
            bidMap.put("bidderCountry", bidderCountry);
            bidMap.put("time", time);
            bidMap.put("amount", amount);

            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
            Date timeDate = new Date();
            try {
                timeDate = inputFormat.parse(time);
            } catch (ParseException e){
                e.printStackTrace();
            }

            dateToBidMap.put(timeDate, bidMap);
        }

        List<Map<String, String>> bidsList = new ArrayList<Map<String, String>>();
        for (Map.Entry<Date, Map<String, String>> bidEntry : dateToBidMap.entrySet()) {
            bidsList.add(bidEntry.getValue());
        }
        return bidsList;
    }

    private static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
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
            s = "N/A";
        }
        return s;
    }
}
