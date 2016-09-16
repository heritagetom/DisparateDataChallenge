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
	
	private void readFile(String filePath) throws IOException{
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
			List<Integer> headersInSheet = searchForHeaders(currSheet);
			List<Integer> headersFiltered = filter(headersInSheet,currSheet);
			System.out.println(headersFiltered);
		}
		
		//Close Workbook and Input Streams
		wb.close();
		mystream.close();	
	}
	
	private List<Integer> searchForHeaders(Sheet worksheet){
		//Get Number of Rows
		Integer numRows = worksheet.getLastRowNum();
		
		//Initiate Header Array
		List<Integer> headerRows = new ArrayList<Integer>();
		
		//Iterate through rows and return content
		for(int row=0;row<numRows;row++){
			Row currRow = worksheet.getRow(row);
			//If row blank - skip to next one
			if(currRow==null){
				continue;
			//Else, get the info of each cell
			}else{
				Map<String,List<Integer>> tempmap = new HashMap<String,List<Integer>>();
				tempmap = getCellInfo(currRow);
				boolean rowCheck = isHeader(tempmap,row);
				//If rowCheck is passed, add this as a header row
				if(rowCheck){
					headerRows.add(row);
				}
			}
		}
		return headerRows;
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
		Integer blankCount = 0;
		for(int cell=0;cell<numCells;cell++){
			Cell currCell = singleRow.getCell(cell);
			//If blank cell - skip
			if(currCell==null || currCell.getCellType()==Cell.CELL_TYPE_BLANK){
				blankCount+=1;
				continue;
			//Else - determine style,format,etc.
			}else{
				//Get style, font, color, and bold(ness)
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
		
		//Get Blank Ratio
		List<Integer> blankArray = new ArrayList<Integer>();
		blankArray.add(blankCount);
		
		//Store Cell Info in Hashmap
		Map<String,List<Integer>> hmap = new HashMap<String,List<Integer>>();
		hmap.put("type", styleArray);
		hmap.put("font", fontArray);
		hmap.put("color", colorArray);
		hmap.put("bold", boldArray);
		hmap.put("blanks", blankArray);
		
		return hmap;
	}
	
	private boolean isHeader(Map<String,List<Integer>> infoMap, Integer rowNum){
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
		
		//Determine if headers based on results
		//System.out.println("Row "+rowNum+": "+boldCheck+", "+colorCheck);
		boolean isheader = check(boldCheck,colorCheck,rowNum);
		return isheader;
	}
	
	private boolean check(boolean boldCheck, boolean colorCheck,Integer currRow){
		//If Row Passes All 3 Test - It's probably a header
		if(boldCheck && colorCheck){
			return true;
	    //Else - if row is first row and bold - It's probably a header
		}else if(boldCheck && currRow<3){
			return true;
		//Else - Not a header
		}else{
			return false;
		}
	}
	
	private List<Integer> filter(List<Integer> headerArray, Sheet currSheet){
		//Iterate through header rows
		Integer priorrow=0;
		
		for(int row=0;row<headerArray.size();row++){
			//If a consecutive row - remove
			Integer headerRow = headerArray.get(row);
			Integer condition = headerRow-1;
			//Remove Duplicates
			if((priorrow==condition) && (row!=0) && (headerArray.get(row-1)!=null)){
				headerArray.set(row,null);
				priorrow = headerRow;
				continue;
			//Else - not a consecutive row, check row below it
			}else{
				//Get Current and Next Row
				Row currRow = currSheet.getRow(headerRow);
				Row nextRow = currSheet.getRow(headerRow+1);
				//Get Non-Empty Cells
				Integer nextRowVal = countCellsWithContent(nextRow);
				Integer currRowVal = countCellsWithContent(currRow);
				//If row below bigger than row above - then row above not header
				if(nextRowVal>currRowVal){
					headerArray.set(row,null);
				}
				priorrow=headerRow;
			}
		}
		
		return headerArray;
		
	}
	
	private Integer countCellsWithContent(Row myRow){
		Integer cellNum = (int) myRow.getLastCellNum();
		Integer counter = 0;
		for(int cell=0;cell<cellNum;cell++){
			Cell currCell = myRow.getCell(cell);
			if(currCell==null || currCell.getCellType()==Cell.CELL_TYPE_BLANK){
				continue;
			}else{
				counter+=1;
			}
		}
		return counter;
	}
}
