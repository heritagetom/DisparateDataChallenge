package challenge.parsers;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import java.io.File;
import challenge.waterquality.*;

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
	private Element rootElement;
	private String searchValue;
	private HashMap<String, DVMap> siteMap = new HashMap<String,DVMap>();
	private boolean foundMatch =false;
	private String matchValue = "";
	Document doc;

	//constructor accepts an XML file, creates a tree structure, and defines its root element
	public DOMXMLParser(File file)
	{
		try 
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			int numAttrs = doc.getDocumentElement().getAttributes().getLength();
			int numElements = doc.getDocumentElement().getChildNodes().getLength();

			//System.out.println("The root element (" + doc.getDocumentElement().getNodeName()+") has "+ numAttrs + " attributes and " + numElements +" elements." );
			rootElement = (Element)doc.getDocumentElement();//create root node
			//System.out.println("-----------------------------------");	
		} 
	 	catch (Exception e) 
		{	    e.printStackTrace();
		}
	}
	
	
	//modifyForES was written to resolve the "siteProperties" issue in the dv
	//xml files. In order for elastic search to accept the json file, the siteProperties
	//node in the original xml must be modified to have one node with a name and
	//value rather than two separate nodes holding the name and value
	
	public void modifyForES(String path) throws TransformerException
	{
		NodeList nList = rootElement.getElementsByTagName("ns1:timeSeries");
		Node queryInfo =rootElement.getFirstChild();
		rootElement.removeChild(queryInfo);
		for(int i=0;i<nList.getLength();i++)
		{
			String value,name;
			Node currentNode = nList.item(i);
			NodeList cList = currentNode.getChildNodes();
			Element ns1values = (Element)cList.item(2);
			Element currentElement = (Element)currentNode;			
			
			//elastic search doesn't like that some values are longs and others
			//are doubles
			Node valueNode = currentElement.getElementsByTagName("ns1:value").item(0);
			Long qualValue = (long)Double.parseDouble(valueNode.getTextContent());
			valueNode.setTextContent(qualValue.toString());
			
			//xml files have an empty object "method description" which 
			//elasticsearch doesn't like. it is removed here.
			/*Node methodDescNode = currentElement.getElementsByTagName("ns1:method").item(0);
			methodDescNode.removeChild(methodDescNode.getFirstChild());
			Node methodID = methodDescNode.getAttributes().getNamedItem("methodID");
			String methodName = methodID.getNodeName();
			String methodVal = methodID.getNodeValue();
			Element newMethodNode = doc.createElement(methodName);
			newMethodNode.setTextContent(methodVal);
			Element methodDescElement = (Element)methodDescNode;
			methodDescElement.removeAttribute("methodID");
			methodDescElement.appendChild(newMethodNode);*/
			
			Element sourceInfo = (Element)cList.item(0);
			//System.out.println("source info node: " + sourceInfo.getNodeName());
			NodeList infoList = sourceInfo.getElementsByTagName("ns1:siteProperty");
			//System.out.println(infoList.getLength());
			for(int j=0;j<infoList.getLength();j++)
			{
				Node curNode = infoList.item(j);
				name = curNode.getAttributes().getNamedItem("name").getNodeValue();
				value = curNode.getTextContent();
				Element curElement = (Element)curNode;
				if(curNode.hasChildNodes())
				{
				curElement.removeAttributeNode((Attr)curNode.getAttributes().item(0));
				curElement.removeChild(curNode.getFirstChild());
				Node n = doc.createElement(name);
				curElement.appendChild(n);
				n.setTextContent(value);
				//System.out.println(curElement.getFirstChild().getNodeName());
				//System.out.println(curElement.getLastChild().getTextContent());
				}

					
			}
						
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}
	
	
	public HashMap<String, DVMap> createMap()
	{
		//Retrieve the list of all time series nodes, each one being a different recording of
		//a daily value. Different nodes, could have the same date, but will have different 
		//method ID's
		NodeList nList = rootElement.getElementsByTagName("ns1:timeSeries");
		for(int i=0;i<nList.getLength();i++)
		{
			Node currentNode = nList.item(i);
			//store site code
			NamedNodeMap attributesMap = currentNode.getAttributes();
			String myString = attributesMap.getNamedItem("name").toString();
			String[] myList = myString.split(":");
			String siteCode = myList[1];
			//store dailyValue
			Element currentElement = (Element)currentNode;
			String value = currentElement.getElementsByTagName("ns1:value").item(0).getTextContent();
			//store date
			NamedNodeMap nMap = currentElement.getElementsByTagName("ns1:value").item(0).getAttributes();
			String date = nMap.item(0).getNodeValue().toString().split("T")[0];
			
			//siteName 
			//String siteName = currentElement.getElementsByTagName("ns1:siteName").item(0).getTextContent();

			//store methodID
			String methodID = currentElement.getElementsByTagName("ns1:method").item(0).getAttributes().item(0).getNodeValue();
			date = date + ":"+methodID;
			
			//if the site exists already in our table, add a new date to its corresponding date map
			if(siteMap.containsKey(siteCode))
			{
				siteMap.get(siteCode).addEntry(date);
				siteMap.get(siteCode).addDV(value);
			}
			// if the site doesn't exist in our map, create a new DVmap for that site code
			else
			{
				DVMap dMap = new DVMap();
				dMap.addEntry(date);
				dMap.addDV(value);
				siteMap.put(siteCode, dMap);
			}		
		}
		return siteMap;
	}
	
	
	//locateValues is a recursive function that finds a specific element or attribute.
	// it first looks for all attributes of a node and secondly, the elements.
	//if a node's element has children of its own it'll find children for that 
	//element before preceding to the following node
	public void locateValue(Node currentNode)
	{	
		if(this.foundMatch)
			return;		
		// create named node map of attributes and store them for current node
		String curNodeName = currentNode.getNodeName();
		String curNodeValue = currentNode.getNodeValue();
		NamedNodeMap attributesMap = currentNode.getAttributes();
		for(int i=0;i<attributesMap.getLength();i++)
		{
			String attrValue = attributesMap.item(i).getNodeValue().toString();
			String attrName = attributesMap.item(i).getNodeName();
			//System.out.println(attrName+ ": "+attrValue);
			if(attributesMap.item(i).getNodeName().equals(this.searchValue))
			{
				this.foundMatch =true;
				this.matchValue = attrValue;
				return;
			}
		}
		//creates node list of child nodes and store them if they have values
		//if the child's value is null, it has children itself, so it'll recursively
		//call the findChildrenValues method until all values are retrieved
		NodeList childrenList = currentNode.getChildNodes();
		Object chNodeValue = "";
		for (int j = 0; j < childrenList.getLength(); j++) 
		{
			Node childNode = childrenList.item(j);
			String chNodeName = childNode.getNodeName();
			chNodeValue = childNode.getNodeValue();
			String chNodeTxt = childNode.getTextContent();
			
			
			if(chNodeValue==null)
			{
				locateValue(childNode);
			}
			
			else if(chNodeValue!=null )
			{
				//System.out.println(curNodeName +": "+chNodeValue);
				if(curNodeName.equals(this.searchValue))
				{
					this.foundMatch =true;
					this.matchValue = chNodeValue.toString();
					return;
				}
			}		
		}
		if(foundMatch == false)
			this.matchValue = this.searchValue+ " not found";
		return;
	}
	
	
	
	//right now, only will return first instance of value
	public String searchFor(String searchValue)
	{
		this.searchValue = searchValue;
		System.out.println("Searching for " + this.searchValue+"...");
		locateValue(rootElement);
		return this.matchValue.toString();
	}
	
}


