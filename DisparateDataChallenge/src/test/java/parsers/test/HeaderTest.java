package parsers.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HeaderTest {
	
	public static void main(String[] args) throws IOException{
		String path = "src/test/resources/EXCEL/example9.xls";
		RowsOfInterest newObj = new RowsOfInterest(path);
		Map<String,ArrayList<Integer>> myMap = newObj.getMap();
		System.out.println(myMap.get("rCSI"));
		//System.out.println(mySet[3].toString());
		//System.out.println(myMap.get(mySet[3].toString())); 
		//HeaderSearch searchObj = new HeaderSearch(path);
	}
}
