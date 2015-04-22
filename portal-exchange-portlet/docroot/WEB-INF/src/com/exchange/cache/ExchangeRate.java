package com.exchange.cache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExchangeRate {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private static final String URL = "http://www.nbrb.by/";
	private static final String WSDL_URL = "http://www.nbrb.by/Services/ExRates.asmx?WSDL";
	private static final String METHOD = "ExRatesDaily";
	
	private static final String SOAP_ACTION = "SOAPAction";	
	private static final String ON_DATE = "onDate";	
	private static final String DAILY_EX = "DailyExRatesOnDate";	
	private static final int QUOTE_INDEX = 0;
	private static final int SCALE_INDEX = 2;
	
	private static Map<String, String> exchangeRate = new LinkedHashMap<String, String>();
	
	public static Map<String, String> getExchangeRates() throws SOAPException{
		if(exchangeRate.size() == 0){
			updateRates();
		}
		return exchangeRate;
	}
	
	public static void updateRates() throws SOAPException{
        SOAPConnectionFactory soapConnectionFactory =  SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();

        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPHeader header = message.getSOAPHeader();
        SOAPBody body = message.getSOAPBody();
        header.detachNode();

        QName bodyName = new QName(URL, METHOD);
        SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
        SOAPElement element = bodyElement.addChildElement(ON_DATE);
        element.setValue(DATE_FORMAT.format(new Date()));
        MimeHeaders headers = message.getMimeHeaders();
        headers.addHeader(SOAP_ACTION, URL + METHOD);
        
        SOAPMessage response = connection.call(message, WSDL_URL);
        connection.close();
        
        NodeList nodeList = response.getSOAPBody().getElementsByTagName(DAILY_EX);
        for(int i=0; i<nodeList.getLength(); i++){
     	   Node node = nodeList.item(i);
     	   String quotName = node.getChildNodes().item(QUOTE_INDEX).getTextContent();
     	   String scale = node.getChildNodes().item(SCALE_INDEX).getTextContent();
     	   exchangeRate.put(quotName, scale);
        }
	}

}
