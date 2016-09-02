package challenge.parsers;

import javax.xml.parsers.DocumentBuilderFactory;
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
	
	public DOMXMLParser(File file)
	{
		this.file = file;
	}
	
	public void printValues()
	{	
		try 
		{
			//File fXmlFile = new File("/Users/thomasheritage/git/DisparateDataChallenge/DisparateDataChallenge/TestFiles/WaterQuality/dv/test.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			System.out.println(doc.getDocumentElement().getChildNodes().getLength());
			Node rootElement = (Element)doc.getDocumentElement();//create root node
			System.out.println("----------------------------");		
			findChidrenValues(rootElement);	//find children of root node
		} 
	 	catch (Exception e) 
		{	    e.printStackTrace();
		}	
	}
	//findChildrenValues is a recursive function that finds children of a node.
	// it first looks for all attributes of a node and secondly, the elements.
	//if a node's element has children of its own it'll find children for that 
	//element before preceding to the following node
	private void findChidrenValues(Node currentNode)
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
		//System.out.println(a.getChildNodes().getLength()); // number of elements
        for (int j = 0; j < childrenList.getLength(); j++) 
		{
			Node childNode = childrenList.item(j);
			if(childNode.getNodeValue()!=null)
			{
				System.out.println(currentNode.getNodeName() +": "+childNode.getNodeValue());
				System.out.println(childNode.hasChildNodes());
			}
			else
				findChidrenValues(childNode);
			
		}
	}

	

	
					
}


