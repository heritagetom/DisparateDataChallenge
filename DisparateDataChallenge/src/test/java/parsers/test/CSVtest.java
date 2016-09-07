package parsers.test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import challenge.parsers.CSV;

public class CSVtest {
	public static void main(String[] args) throws IOException{
		//Path
		String path = "TestFiles/HumanitarianData/DataForWorldFoodProgram/CSV/example.csv";
		
		//Set Up My Object
		CSV csvobj = new CSV(path);
		Map<String,List<String>> hmap = csvobj.getMap();
		
		//Set Up CSV Reader Data
		FileReader testfile = new FileReader(path);
		Iterable<CSVRecord> csvtest = CSVFormat.EXCEL.parse(testfile);
		String headerTest = checkHeaders(hmap,csvtest);
		String rowTest = checkRowLength(hmap,csvtest);
		
		//Print Results
		System.out.println("Header Test: "+headerTest);
		System.out.println("Row Test: "+rowTest);
	}
	
	private static String checkHeaders(Map<String,List<String>> testmap,Iterable<CSVRecord> csvtest){
		//Get Headers for Map
		String headerString = null;
		Set<String> keySet = testmap.keySet();
		List<String> keyArray = new ArrayList<String>(keySet);
		CSVRecord firstline = csvtest.iterator().next();
		
		//Convert CSVRecord to List
		List<String> headerArray = new ArrayList<String>();
		for(int i=0;i<firstline.size();i++){
			String tempString = firstline.get(i);
			headerArray.add(tempString);
		}
		
		//Sort Arrays
		Collections.sort(keyArray);
		Collections.sort(headerArray);
		
		//Compare Keys and Header
		if(keyArray.equals(headerArray)){
			headerString = "Pass";
		}else{
			headerString = "Fail";
		}
		return headerString;
	}
	
	private static String checkRowLength(Map<String,List<String>> testmap,Iterable<CSVRecord> csvtest){
		//Get Actual Number of Rows
		String rowString = null;
		Integer rowCount = 0;
		Integer actualRowLength = 0;
		while(csvtest.iterator().hasNext()){
			actualRowLength +=1;
		}
		
		//Get KeySet
		Set<String> keySet = testmap.keySet();
		
		//Iterate through keys
		for(String key:keySet){
			Integer rowLength = testmap.get(key).size();
			if(rowLength.equals(actualRowLength)){
				continue;
			}else{
				rowCount+=1;
			}
		}
		
		//Record Pass/Fail
		if(rowCount>0){
			rowString="Fail";
		}else{
			rowString="Pass";
		}
		
		return rowString;
	}
}
