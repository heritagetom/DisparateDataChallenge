package challenge.parsers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class CSV{
	private String mypath = null;
	private List<String> keys = null;
	private Map<String,List<String>> map = null;
	
	public CSV(String inpath){
		this.mypath = inpath;
	}

	public Map<String,List<String>> getMap() throws IOException{
		FileReader inputread = new FileReader(this.mypath);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(inputread);
		List<String> newkeys = getHeaders(records);
		this.keys = newkeys;
		this.map = getData(records,newkeys);
		return this.map;
	}
	
	public List<String> getKeys(){
		return this.keys;
	}
	
	private static List<String> getHeaders(Iterable<CSVRecord> rowdata) throws IOException{
		List<String> keys = new ArrayList<String>();
		CSVRecord headerlines = rowdata.iterator().next();
		for(int width=0;width<headerlines.size();width++){
			keys.add(headerlines.get(width));
		}
		return keys;
	}

	private static Map<String,List<String>> getData(Iterable<CSVRecord> linedata, List<String> headers){
		Map<String,List<String>> finalmap = new HashMap<String,List<String>>();
		for(CSVRecord record: linedata){
			for(int width=0;width<headers.size();width++){
				String newVal = record.get(width);
				String currKey = headers.get(width);
				List<String> test = finalmap.get(currKey);
				
				if(test==null){
					List<String> currVals = new ArrayList<String>();
					currVals.add(newVal);
					finalmap.put(currKey, currVals);
				}else{
					List<String> currVals = finalmap.get(currKey);
					currVals.add(newVal);
					finalmap.put(currKey, currVals);
				}
			}
		}
		return finalmap;		
	}
}
