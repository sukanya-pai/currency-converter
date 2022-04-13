package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component
public class ExchangeRateXMLConverter {

    public HashMap<String,String> exchangeRateConverter(String exchangeRateXMLString) {
        HashMap<String,String> currencyRateMap = new HashMap<>();


        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {

            document = builder.parse(new InputSource(new StringReader(exchangeRateXMLString)));

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            String xPathString = CurrencyRatesConstants.CUBE_NODE;
            XPathExpression expr = xpath.compile(xPathString);
            NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                NamedNodeMap attributes = node.getAttributes();
                if (attributes.getLength() > 0) {
                    Node currencyAttribute = attributes.getNamedItem(CurrencyRatesConstants.CURRENCY);
                    if (currencyAttribute != null) {
                        String currencyTxt = currencyAttribute.getNodeValue();
                        String rateTxt = attributes.getNamedItem(CurrencyRatesConstants.RATE).getNodeValue();
                        currencyRateMap.put(currencyTxt, rateTxt);
                    }
                }
            }
        } catch (SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return currencyRateMap;
    }
}