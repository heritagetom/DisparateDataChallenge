package challenge.parsers;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XLSX {
	private String path = null;
	private Map<String, HashMap<String, ArrayList<String>>> finalmap = new HashMap<String,HashMap<String,ArrayList<String>>>();
	
	public XLSX(String newpath){
		this.path = newpath;
		try {
			this.finalmap = readFile(this.path);
		} catch (IOException e) {
			this.finalmap = null;
		}
	}
	
	public Map<String,HashMap<String,ArrayList<String>>> getMap(){
		return this.finalmap;
	}
	
	public String getPath(){
		return this.path;
	}
	
	private Map<String,HashMap<String,ArrayList<String>>> readFile(String currpath) throws IOException{
		//Load EXCEL File
		File myfile = new File(currpath);
		FileInputStream mystream = new FileInputStream(myfile);
		Workbook wb;
		
		//Change Object Type Depending on XLS vs. XLSX
		if(currpath.endsWith("xlsx")){
			wb = new XSSFWorkbook(mystream);
		}else if(currpath.endsWith("xls")){
			wb = new HSSFWorkbook(mystream);
		}else{
			mystream.close();
			throw new IllegalArgumentException("This is not an Excel Workbook");
		}
		//Get Number of Sheets in File
		Integer numSheets = wb.getNumberOfSheets();
		
		//Create Empty HashMap
		Map<String, HashMap<String,ArrayList<String>>> outermap = new HashMap<String,HashMap<String,ArrayList<String>>>();
		
		//Iterate Through Sheets
		for(int i=0;i<numSheets;i++){
			Sheet currSheet = wb.getSheetAt(i);
			HashMap<String,ArrayList<String>> insidemap = parseSheet(currSheet);
			outermap.put(currSheet.getSheetName(), insidemap);
		}
		
		//Close Workbook and Input Streams
		wb.close();
		mystream.close();
		
		return outermap;
	}
		
	private HashMap<String,ArrayList<String>> parseSheet(Sheet sheet){
		//Get Rows as Iterators
		Iterator<Row> iterator = sheet.iterator();
		
		//Generate Empty HashMaps
		HashMap<String,ArrayList<String>> innerMap = new HashMap<String,ArrayList<String>>();
		
		//Get Column Header
		Row headers = iterator.next();
		List<String> keys = getKeys(headers);
		
		//Iterate Through Rows
		while(iterator.hasNext()){
			Row nextRow = iterator.next();
			innerMap = getValues(keys,innerMap, nextRow);
		}
		
		return innerMap;
	}
	
	private HashMap<String,ArrayList<String>> getValues(List<String> keyValues, HashMap<String,ArrayList<String>> hmap, 
			Row valueRow){
		//Set Up Formatter and Output List
		DataFormatter df = new DataFormatter();
		short totalCells = valueRow.getLastCellNum();
		
		//If Num Cells Does Not Equal Num Headers - Add Value at End
		if(totalCells<keyValues.size()){
			valueRow.createCell(totalCells);
		}
		totalCells = valueRow.getLastCellNum();
		
		//Iterate Through Cells
		Integer counter=0;
		for(int cn=0;cn<totalCells;cn++){
			String currKey = keyValues.get(counter);
			Cell currCell = valueRow.getCell(cn,Row.CREATE_NULL_AS_BLANK);
			String cellValueString = df.formatCellValue(currCell);
			
			//If HashMap has been initialized
			if(hmap.containsKey(currKey)){
				ArrayList<String> currList = hmap.get(currKey);
				currList.add(cellValueString);
				hmap.put(currKey, currList);
			//Else - the ArrayList has not been initialized
			}else{
				ArrayList<String> currList = new ArrayList<String>();
				currList.add(cellValueString);
				hmap.put(currKey, currList);
			}
			counter+=1;
		}
		return hmap;
	}
	
	private List<String> getKeys(Row headerRow){
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


}
