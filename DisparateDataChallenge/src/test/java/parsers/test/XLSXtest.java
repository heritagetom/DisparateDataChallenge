package parsers.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import challenge.parsers.XLSX;

public class XLSXtest {
	
	public static void main(String[] args) throws IOException{
		//List all files in resource directory
		String dirpath = "src/test/resources/EXCEL/";
		File folder = new File(dirpath);
		File[] files = folder.listFiles();
		
		for(File file:files){
			//Path
			file = files[8];
			String testpath = file.getPath();
			System.out.println(testpath);
			
			//Load File Using POI from Apache
			File testfile = new File(testpath);
			FileInputStream teststream = new FileInputStream(testfile);
			
			//Initialize Test Variables
			Workbook wb = null;
			
			if(testpath.endsWith("xlsx")){
				//Define XLSX Workbook
				wb = new XSSFWorkbook(teststream);
				
			}else if(testpath.endsWith("xls")){
				//Define XLS Workbook
				wb = new HSSFWorkbook(teststream);	
				
			}else if(testpath.endsWith("xlsm")){
				//Define XLSM Workbook
				wb = new XSSFWorkbook(teststream);
				
			}else{
				teststream.close();
				continue;
			}
			
			//Test Data Structure
			XLSX testobj = new XLSX(testpath);
			Map<String,HashMap<String,ArrayList<String>>> testmap = testobj.getMap();
			
			//Run Unit Tests
			String sheetNumTest = compareNumTabs(testmap,wb);
			String sheetNameTest = compareAllTabs(testmap,wb);
			String headerNamesTest = compareAllHeaders(testmap,wb);
			String rowNumTest = checkRowNumbers(testmap,wb);
			String consistencyTest = compareMapConsistency(testmap);
			
			//Print Unit Test Results
			System.out.println("Number of Sheets: "+sheetNumTest);
			System.out.println("Names of Sheets: "+sheetNameTest);
			System.out.println("Header Names: "+headerNamesTest);
			System.out.println("Number of Rows: "+rowNumTest);
			System.out.println("Consistent Number of Values in Map: "+consistencyTest);
			System.out.println("");
			
			teststream.close();
			break;
		}
	}
	
	public static String compareNumTabs(Map<String,HashMap<String,ArrayList<String>>> hmapTest,Workbook workbook) throws IOException{
		
		//Out String
		String sheetString=null;
		
		//Get Number of Sheets in Workbook
		Integer testSheets = workbook.getNumberOfSheets();
		
		//Get Number of Sheets From HMap - Doesn't Account for Duplicates
		Integer mySheets = hmapTest.keySet().size();
		
		//Compare Numbers and print out Pass/Fail
		if(mySheets.equals(testSheets)){
			sheetString = "Pass";
		}else{
			sheetString = "Fail";
		}
		return sheetString;
	}
	
	public static String compareAllTabs(Map<String,HashMap<String,ArrayList<String>>> hmapTest,Workbook workbook) throws IOException{
		//Out String
		String tabString=null;

		//Tab Names
		List<String> sheetNamesPOI = new ArrayList<String>();
		Integer numSheets = workbook.getNumberOfSheets();
		for(int i=0;i<numSheets;i++){
			String currSheet = workbook.getSheetName(i);
			sheetNamesPOI.add(currSheet);
		}
		
		//Get Key Names From HMap
		Set<String> hmapNames = hmapTest.keySet();
		List<String> hmapNamesList = new ArrayList<String>(hmapNames);
		
		if(hmapNamesList.containsAll(sheetNamesPOI) && hmapNamesList.size()==sheetNamesPOI.size()){
			tabString = "Pass";
		}else if (hmapNamesList.containsAll(sheetNamesPOI) && hmapNamesList.size()!=sheetNamesPOI.size()){
			tabString = "Tab Names Equal, But Number Differs";
		}else{
			tabString = "Fail";
		}
		
		return tabString;
	}
	
	public static String compareAllHeaders(Map<String,HashMap<String,ArrayList<String>>> hmapTest,Workbook workbook){
		
		String headerString = null;
		Integer numSheets = workbook.getNumberOfSheets();
		for(int i=0;i<numSheets;i++){
			//Get Sheet
			Sheet currSheet = workbook.getSheetAt(i);
			
			//Get First Row and Headers
			Iterator<Row> rows = currSheet.iterator();
			Row headers = rows.next();
			List<String> headerList = getKeys(headers);
			
			//Get Sub-Key Names
			String sheetName = currSheet.getSheetName();
			Set<String> keySet = hmapTest.get(sheetName).keySet();
			List<String> keySetList = new ArrayList<String>(keySet);
			
			//Compare
			if(keySetList.containsAll(headerList) && keySetList.size()==headerList.size()){
				headerString = "Pass";
			}else if (keySetList.containsAll(headerList) && keySetList.size()!=headerList.size()){
				headerString = "Tab Names Equal, But Number Differs";
				System.out.println(headerList.size());
				System.out.println(keySetList.size());
				System.out.println(currSheet.getSheetName());
				break;
			}else{
				headerString = "Fail";
				System.out.println("Sheet: "+sheetName);
				System.out.println("Headers from POI: "+headerList);
				System.out.println("Headers from HMAP: "+keySetList);
				break;
			}
		}
		return headerString;
	}
	
	public static String checkRowNumbers(Map<String,HashMap<String,ArrayList<String>>> hmapTest,Workbook workbook){
		String rowString = null;
		Integer numSheets = workbook.getNumberOfSheets();
		for(int i=0;i<numSheets;i++){
			//Get Sheet
			Sheet currSheet = workbook.getSheetAt(i);
			
			//Get Number of Rows in Sheet
			Integer actualArraySize = currSheet.getPhysicalNumberOfRows();
			
			//Get Sub-Key Names
			String sheetName = currSheet.getSheetName();
			Set<String> keySet = hmapTest.get(sheetName).keySet();
			for(String key:keySet){
				List<String> tempArray = hmapTest.get(sheetName).get(key);
				Integer hmapArraySize = tempArray.size();
				//Subtract One for the Header
				if(hmapArraySize.equals(actualArraySize-1)){
					rowString = "Pass";
				}else{
					System.out.println("--");
					rowString = "Fail";
					System.out.println("Row Test: "+rowString);
					System.out.println(keySet);
					System.out.println("Sheet: "+sheetName);
					System.out.println("Header: "+key);
					System.out.println("HMap Size: "+hmapArraySize);
					System.out.println("Actual Size: "+(actualArraySize-1));
					System.out.println("HMap Values: "+tempArray);
					break;
				}
			}
		}
		return rowString;
	}
	
	private static List<String> getKeys(Row headerRow){
		//Set Up Formatter and Output List
		DataFormatter df = new DataFormatter();
		Iterator<Cell> headerCell = headerRow.cellIterator();
		List<String> headers = new ArrayList<String>();
		
		//Iterate Through Cells
		while(headerCell.hasNext()){
			Cell currCell = headerCell.next();
			String cellValueString = df.formatCellValue(currCell);
			headers.add(cellValueString);
		}
		
		return headers;
	}
	
	private static String compareMapConsistency(Map<String,HashMap<String,ArrayList<String>>> hmapTest){
		//Initialize Variable
		Integer errorNum = 0;
		String consistencyString = null;
		Set<String> keySet1 = hmapTest.keySet();
		
		//Iterate Through First Key (Sheets)
		for(String key1:keySet1){
			HashMap<String,ArrayList<String>> innerMap = hmapTest.get(key1);
			Set<String> keySet2 = innerMap.keySet();
			
			//Iterate Through Second Key Set (Column Headers)
			Object[] keyArray = keySet2.toArray();
			Integer first = innerMap.get(keyArray[0]).size();
			for(String key2:keySet2){
				Integer valueSize = innerMap.get(key2).size();
				if(valueSize.equals(first)){
					continue;
				}else{
					errorNum+=1;
				}
			}
		}
		if(errorNum>0){
			consistencyString="Fail";
		}else{
			consistencyString="Pass";
		}
		return consistencyString;
	}
}
