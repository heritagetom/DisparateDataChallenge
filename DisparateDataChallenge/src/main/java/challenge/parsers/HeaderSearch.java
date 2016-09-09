package challenge.parsers;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HeaderSearch {
	private String path = null;
	private List<String> headers = null;
	private Integer blankColorIndex = 64;
	private Workbook internalWb = null;
	
	public HeaderSearch(String newpath){
		this.path = newpath;
		try {
			readFile(this.path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readFile(String filePath) throws IOException{
		File xlsfile = new File(filePath);
		FileInputStream mystream = new FileInputStream(xlsfile);
		Workbook wb;
		
		//Change Object Type Depending on XLS vs. XLSX
		if(filePath.endsWith("xlsx")){
			wb = new XSSFWorkbook(mystream);
		}else if(filePath.endsWith("xls")){
			wb = new HSSFWorkbook(mystream);
		}else if(filePath.endsWith("xlsm")){
			wb = new XSSFWorkbook(mystream);
		}else{
			mystream.close();
			throw new IllegalArgumentException("This is not an Excel Workbook");
		}
		//Get Number of Sheets in File
		this.internalWb = wb;
		Integer numSheets = wb.getNumberOfSheets();
		
		//Iterate Through Sheets
		for(int i=0;i<numSheets;i++){
			Sheet currSheet = wb.getSheetAt(i);
			System.out.println("====== Sheet: "+currSheet.getSheetName()+" ======");
			searchForHeaders(currSheet);
		}
		
		//Close Workbook and Input Streams
		wb.close();
		mystream.close();
		
		//return this.headers;
	}
	
	private void searchForHeaders(Sheet worksheet){
		//Get Number of Rows
		Integer numRows = worksheet.getLastRowNum();
		
		//Iterate through rows and return content
		for(int row=0;row<numRows;row++){
			System.out.println("--Row: "+row);
			Row currRow = worksheet.getRow(row);
			if(currRow==null){
				continue;
			}else{
				Map<String,List<Integer>> tempmap = new HashMap<String,List<Integer>>();
				tempmap = getCellInfo(currRow);
				boolean rowCheck = isHeader(tempmap);
				System.out.println("Is Row Header: "+rowCheck);
			}
		}	
	}
	
	private Map<String,List<Integer>> getCellInfo(Row singleRow){
		//Get Number of Cells
		short numCells = singleRow.getLastCellNum();
		
		//Generate Lists to Record Cell Info
		List<Integer> styleArray = new ArrayList<Integer>();
		List<Integer> fontArray = new ArrayList<Integer>();
		List<Integer> colorArray = new ArrayList<Integer>();
		List<Integer> boldArray = new ArrayList<Integer>();
		
		//Iterate through cells
		for(int cell=0;cell<numCells;cell++){
			Cell currCell = singleRow.getCell(cell);
			if(currCell==null || currCell.getCellType()==Cell.CELL_TYPE_BLANK){
				continue;
			}else{
				styleArray.add(currCell.getCellType());
				fontArray.add((int) currCell.getCellStyle().getFontIndex());
				colorArray.add((int) currCell.getCellStyle().getFillBackgroundColor());
				short fontStyleIndex = currCell.getCellStyle().getFontIndex();
				if(this.internalWb.getFontAt(fontStyleIndex).getBoldweight()==Font.BOLDWEIGHT_BOLD){
					boldArray.add(1);
				}else{
					boldArray.add(0);
				}
			}
		}
		
		//Store Cell Info in Hashmap
		Map<String,List<Integer>> hmap = new HashMap<String,List<Integer>>();
		hmap.put("type", styleArray);
		hmap.put("font", fontArray);
		hmap.put("color", colorArray);
		hmap.put("bold", boldArray);
		
		System.out.println("Cell Type: "+styleArray);
		System.out.println("Font Type: "+fontArray);
		System.out.println("Color Type: "+colorArray);
		System.out.println("Bold Type: "+boldArray );
		
		return hmap;
	}
	
	private boolean isHeader(Map<String,List<Integer>> infoMap){
		//Initialize Variables
		boolean colorCheck = false;
		boolean boldCheck = false;
		
		//Check Color
		List<Integer> colorArray = infoMap.get("color");
		for(int i=0;i<colorArray.size();i++){
			if(colorArray.get(i)!=this.blankColorIndex){
				colorCheck = true;
				continue;
			}else{
				colorCheck = false;
				break;
			}
		}
		
		//Font Check (Bold)
		List<Integer> boldArray = infoMap.get("bold");
		for(int j=0;j<boldArray.size();j++){
			if(boldArray.get(j)>0){
				boldCheck = true;
				break;
			}else{
				boldCheck = false;
				continue;
			}
		}
		
		boolean isheader = check(boldCheck,colorCheck);
		return isheader;
	}
	
	private boolean check(boolean boldCheck, boolean colorCheck){
		//If Row Passes All 3 Test - It's probably a header
		if(boldCheck || colorCheck){
			return true;
		}else{
			return false;
		}
	}
}
