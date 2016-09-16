package harvester.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import challenge.waterquality.DVHarvester;
import challenge.converters.*;
import org.json.JSONObject;
import challenge.parsers.*;


public class DVHarvesterTest {
	
	static String[] states = new String[]{"AR"/*,"AK","AL","AZ","CA","CO","CT","DE","DC","FL","GA","HI",
										"ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN",
										"MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND",
										"OH","OK","OR","PA","PR","RI","SC","SD","TN","TX","UT","VT",
										"VA","WA","WV","WI","WY"*/};
	static XMLtoJSON converter = new XMLtoJSON();
	public static void main(String[] args) throws IOException, InterruptedException, TransformerException{
		
		//Test Database functionality
		for(int i=0;i<states.length;i++)
		{
		DVHarvester testDatabase = new DVHarvester();
		testDatabase.addFormat("xml");

		testDatabase.addState(states[i]);
		String mainstring = testDatabase.getPath();
		System.out.println(testDatabase.getPath());
		
		//for each state query, create an xml file with its information 
		String parentdir = "/Users/thomasheritage/Desktop/DVStates/";
		File file = new File(parentdir+states[i]+".xml");

		URL url = new URL(mainstring);
		FileUtils.copyURLToFile(url,file);
		DOMXMLParser parser = new DOMXMLParser(file);
		parser.modifyForES(parentdir+states[i]+".xml");
		
		//for each state, convert the xml file to a JSONObject and create a JSON file
		FileWriter fw = new FileWriter("/Users/thomasheritage/Desktop/JsonDvStates/" + states[i]+".json");
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj = converter.convertFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fw.write(jsonObj.toString());
		fw.close();
		System.out.println("finished " + states[i]);
		}
		System.out.println("done converting");
		
	}

}
