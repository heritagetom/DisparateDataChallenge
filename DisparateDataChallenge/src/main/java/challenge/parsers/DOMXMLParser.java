package challenge.parsers;

import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import java.io.File;

/* 
 * Tom Heritage
 * Booz | Allen | Hamilton
 * Aug. 31 2016
 * This class is an XML parser to retrieve data from NGA publicly accessible
 * databases for their Disparate Data Challenge and create a tree of the data
 * See https://www.challenge.gov/challenge/disparate-data-challenge/
 * for more information.
 */

public class DOMXMLParser
{
	File file;
	Element rootElement;
	String searchValue;
	HashMap<String, HashMap<String, String>> siteIdMap = new HashMap<String, HashMap<String, String>>();
	HashMap<String, String> dateMap = new HashMap<String, String>(); 
	
	public DOMXMLParser(File file)
	{
		this.file = file;
		try 
		{
			//File fXmlFile = new File("/Users/thomasheritage/git/DisparateDataChallenge/DisparateDataChallenge/TestFiles/WaterQuality/dv/test.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			System.out.println(doc.getDocumentElement().getChildNodes().getLength());
			rootElement = (Element)doc.getDocumentElement();//create root node
			System.out.println("----------------------------");		
		} 
	 	catch (Exception e) 
		{	    e.printStackTrace();
		}
	}
	
	
	public HashMap<String, HashMap<String, String>> createMap()
	{
		NodeList nList = rootElement.getElementsByTagName("ns1:timeSeries");
		for(int i=0;i<nList.getLength();i++)
		{
			Node currentNode = nList.item(i);
			NamedNodeMap attributesMap = currentNode.getAttributes();
			String myString = attributesMap.getNamedItem("name").toString();
			String[] myList = myString.split(":");
			String siteId = myList[1];//site code
			
			Element currentElement = (Element)currentNode;
			String value = currentElement.getElementsByTagName("ns1:value").item(0).getTextContent().substring(0,3);//value
			NamedNodeMap map = currentElement.getElementsByTagName("ns1:value").item(0).getAttributes();
			String date = map.item(0).getNodeValue().toString();//date
		}
		return siteIdMap;
	}
	
	
	//findChildrenValues is a recursive function that finds children of a node.
	// it first looks for all attributes of a node and secondly, the elements.
	//if a node's element has children of its own it'll find children for that 
	//element before preceding to the following node
	/*private void findChildrenValues(Node currentNode)
	{	
		System.out.println("\ncurrently inside of " + currentNode.getNodeName());
		// create named node map of attributes and store them for current node
		NamedNodeMap attributesMap = currentNode.getAttributes();
		for(int i=0;i<attributesMap.getLength();i++)
		{
		System.out.println(attributesMap.item(i).getNodeName()+ ": "+attributesMap.item(i).getNodeValue());
		}
		//creates node list of child nodes and store them if they have values
		//if the child's value is null, it has children itself, so it'll recursively
		//call the findChildrenValues method until all values are retrieved
		NodeList childrenList = currentNode.getChildNodes();
		
		for (int j = 0; j < childrenList.getLength(); j++) 
		{
			Node childNode = childrenList.item(j);
			if(childNode.getNodeValue()!=null)
			{
				System.out.println(currentNode.getNodeName() +": "+childNode.getNodeValue());
				System.out.println(childNode.hasChildNodes());
			}
			else
				findChildrenValues(childNode);
			
		}
	}*/
	
	
	/*public String searchFor(String searchValue)
	{
		this.searchValue = searchValue;
		return returnValueFrom(rootElement);
	}
	
	
	private String returnValueFrom (Node currentNode)
	{
		NamedNodeMap attributesMap = currentNode.getAttributes();
		for(int i=0;i<attributesMap.getLength();i++)
		{	
		String attrName = attributesMap.item(i).getNodeName();
		//System.out.println("current attr is "+ attrName+": " + attributesMap.item(i).getNodeValue());
		if(attrName == this.searchValue)
			return (String)attributesMap.item(i).getNodeValue();
		}

		NodeList childrenList = currentNode.getChildNodes();
        for (int j = 0; j < childrenList.getLength(); j++) 
		{
			Node childNode = childrenList.item(j);
			//System.out.println("current node is "+ currentNode.getNodeName());
			//System.out.println("current node value is  "+ currentNode.getNodeValue());
			//System.out.println("child value is "+ childNode.getNodeValue());			
			if(childNode.getNodeValue()!=null)
			{
				if(currentNode.getNodeName() == searchValue)
					return (String)childNode.getNodeValue();
			}
			else
				returnValueFrom(childNode);
		}
		//System.out.println("No value found for your request of " + searchValue );
		return "";			
	}*/
}


