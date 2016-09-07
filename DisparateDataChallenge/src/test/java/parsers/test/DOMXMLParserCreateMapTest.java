package parsers.test;
import java.io.File;
import challenge.waterquality.*;
import challenge.parsers.DOMXMLParser;
import java.util.HashMap;
import static org.junit.Assert.*;

/* 
* Tom Heritage
* Booz | Allen | Hamilton
* Aug. 31 2016
* This class is an DOMXML parser test to retrieve data from NGA publicly accessible
* databases for their Disparate Data Challenge, creating a hash map of data from test2.xml
* See https://www.challenge.gov/challenge/disparate-data-challenge/
* for more information.
*/

public class DOMXMLParserCreateMapTest {
	public static void main(String[] args)
	{			
		//create a parser object and send it the xml file
		File fXmlFile = new File("/Users/thomasheritage/git/DisparateDataChallenge/DisparateDataChallenge/TestFiles/Other/WaterQuality/dv/test9.xml");
		DOMXMLParser myParser = new DOMXMLParser(fXmlFile);
		//call createMap to store xml data in a hash map structure
		HashMap<String,DVMap> myMap = myParser.createMap();
		
		assertEquals("Number of site locations should be 4",4,myMap.size());
		
		//for each site code, list its water quality recording entries		
		for (String key : myMap.keySet()) 
			{
	    	assertTrue("Should be at least one data entry for current site",myMap.get(key).hmap.keySet().size()>0);
		    System.out.println("site code:\t"+ key);
	    	System.out.print("entries:\t");
	    	
		    for(String time: myMap.get(key).hmap.keySet())
		    {
		    	Double value = myMap.get(key).hmap.get(time).getDV();
		    	assertNotNull("time shouldn't be null", time);
		    	assertNotNull("value shouldn't be null",value);   	
		    	
		    	System.out.print(time + " ");
		    	System.out.print("value= "+value+" ");
		    	System.out.println();
		    	System.out.print("\t\t");
		    }
		    System.out.println();
			}
		
		
	}
	
	
}
