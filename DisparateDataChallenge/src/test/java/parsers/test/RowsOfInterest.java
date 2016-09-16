package parsers.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RowsOfInterest {
	private Workbook workbook = null;
	private Map<String,ArrayList<Integer>> headers = new HashMap<String,ArrayList<Integer>>();
	
	//Constructor
	public RowsOfInterest(String filepath) throws IOException{
		getWorkbook(filepath);
		getHeaderRows();
		filterHeaders();
	}
	
	public Map<String,ArrayList<Integer>> getMap(){
		return this.headers;
	}
	
	public Workbook getWorkbook(){
		return this.workbook;
	}
	
	//Get Workbook - Throw IO Error
	private void getWorkbook(String myPath) throws IOException{
		//Initialize File
		File myfile = new File(myPath);
		FileInputStream mystream = new FileInputStream(myfile);
		//Change Object Type Depending on XLS vs. XLSX
		if(myPath.endsWith("xlsx")){
			this.workbook = new XSSFWorkbook(mystream);
		}else if(myPath.endsWith("xls")){
			this.workbook = new HSSFWorkbook(mystream);
		}else if(myPath.endsWith("xlsm")){
			this.workbook = new XSSFWorkbook(mystream);
		}else{
			mystream.close();
			throw new IllegalArgumentException("This is not an Excel Workbook");
		}
	}
	
	//Get Rows Of Interest
	private void getHeaderRows(){
		Integer numSheets = this.workbook.getNumberOfSheets();
		//Iterate Through Sheets
		for(int i=0;i<numSheets;i++){
			Sheet currSheet = this.workbook.getSheetAt(i);
			findRowsInSheet(currSheet);
		}
	}
	
	//Search Rows in Sheet
	private void findRowsInSheet(Sheet mySheet){
		//Initialize Row Info
		Integer priorCellNum = null;
		Integer startRow = mySheet.getFirstRowNum();
		Integer endRow = mySheet.getLastRowNum();
		ArrayList<Integer> headerArray = new ArrayList<Integer>();
		//Iterate Through Rows
		for(int j=startRow;j<endRow;j++){
			Row currRow = mySheet.getRow(j);
			if(currRow==null){
				continue;
			}
			//Count Cells with Content
			Integer cellNum = countCellsWithContent(currRow);
			//If cell span does not equal prior cell span (maybe change to greater than)
			if(cellNum!=priorCellNum || j==startRow){
				headerArray.add(j);
				priorCellNum=cellNum;
			}else{
				priorCellNum=cellNum;
			}
		}
		this.headers.put(mySheet.getSheetName().toString(), headerArray);
	}
	
	//Search Cells in Row - Return Number of Cells with Content
	private Integer countCellsWithContent(Row myRow){
		Integer cellNum = (int) myRow.getLastCellNum();
		Integer end = 0;
		Integer start = 0;
		//Iterate Through Cells - Find Start with Content
		for(int cell=0;cell<cellNum;cell++){
			Cell currCell = myRow.getCell(cell);
			if(currCell==null || currCell.getCellType()==Cell.CELL_TYPE_BLANK){
				continue;
			}else{
				start = cell;
				break;
			}
		}
		//Iterate through cells from start - find last cell
		for(int cell=start;cell<cellNum;cell++){
			Cell currCell = myRow.getCell(cell);
			if(currCell==null || currCell.getCellType()==Cell.CELL_TYPE_BLANK){
				continue;
			}else{
				end = cell;
			}
		}
		//Calculate Difference
		Integer count = end-start;
		return count;
	}
	
	private void filterHeaders(){
		//Iterate Through Header Keys
		for(String key:this.headers.keySet()){
			System.out.println(key);
			Sheet currSheet = this.workbook.getSheet(key);
			ArrayList<Integer> currHeaders = this.headers.get(key);
			System.out.println("Old Headers: "+currHeaders);
			ArrayList<Integer> filterHeaders = checkRowSize(currSheet,currHeaders);
			System.out.println("New Headers: "+filterHeaders);
			this.headers.remove(key);
			this.headers.put(key, filterHeaders);
		}
	}
	
	private ArrayList<Integer> checkRowSize(Sheet mySheet, ArrayList<Integer> rows){
		Integer count = 1;
		while(count<rows.size()){
			if(rows.size()==1){
				break;
			}
			Integer rowDiff = rows.get(count)-rows.get(count-1);
			if(rowDiff==1){
				Integer cellsAbove = countCellsWithContent(mySheet.getRow(rows.get(count-1)));
				Integer cellsBelow = countCellsWithContent(mySheet.getRow(rows.get(count)));
				if(cellsBelow>cellsAbove){
					rows.remove(count-1);
				}else{
					count+=1;
				}	
			}	
		}
		return rows;
	}
}
