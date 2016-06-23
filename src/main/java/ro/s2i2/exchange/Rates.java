//
// Rates.java
//
package ro.s2i2.exchange;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Rates {
    private HashMap<String, Double> rates;

    public Rates(String ratefile) {
        try {
            rates = Rates.parseRate(ratefile);
        }
        catch(Exception e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    public static HashMap<String, Double> parseRate(String ratefile) throws Exception {
        Logger logger = Logger.getLogger(Rates.class);

        logger.debug("Start parsing" + ratefile);

        //Get Docuemnt Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = null;
        try {
            document = builder.parse(new File(ratefile));
        }
        catch( SAXException e ) {
            System.err.println("Caught SAXException: " + e.getMessage());
        }
        catch( IOException  e ) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        document.getDocumentElement().normalize();

        // Get the date attribute of the Cube element
        NodeList nList0 = document.getElementsByTagName("Cube");
        Node node0 = nList0.item(0);
        if (node0.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node0;
            System.out.println("Date: " + element.getAttribute("date"));
        }

        NodeList nList = document.getElementsByTagName("Rate");
        int howMany = nList.getLength();

        HashMap<String, Double> rates = new HashMap<String, Double>();

        for (int temp = 0; temp < howMany; temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String currency = element.getAttribute("currency");
                Double rate     = Double.parseDouble(element.getTextContent());
                if ( element.hasAttribute("multiplier") ) {
                    int multiplier = Integer.parseInt(element.getAttribute("multiplier"));
                    rate *= multiplier;
                }
                rates.put(currency, rate);
                if( logger.isDebugEnabled() ) {
                    logger.debug("Currency: " + currency + ", Rate: " + rate);
                }
            }
        }

        logger.debug("Finished.");

        return rates;
    }

    /**
     * Returns the rate for a currency.
     *
     * @param currency the currency
     * @throws IllegalArgumentException if the currency is not known
     */
    public Double getRate(String currency) {
        Double rate = rates.get(currency);
        if (rate == null) {
            throw new IllegalArgumentException("Invalid currency:" + currency);
        }
        return rate;
    }
}
