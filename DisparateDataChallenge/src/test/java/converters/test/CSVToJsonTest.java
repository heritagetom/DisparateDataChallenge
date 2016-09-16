package converters.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CSVToJsonTest {

	public static void main(String[] args) {
		//Path
		String path = "TestFiles/example2.csv";
		try {

			//Read CSV File
			BufferedReader csvFile = new BufferedReader(new FileReader(path));
			String headerLine = csvFile.readLine();
			ArrayList<String> headers = retrieveHeaders(headerLine);

			//Set up new JSON File
			File file=new File("C:\\Users\\586571\\Documents\\Projects\\JSONFiles\\example2.json");  
			//file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);  

			//Set up JSON objects
			JSONArray jsonObjectList = new JSONArray(); 
			JSONObject jsonContent = new JSONObject();
			//Get list of JSON objects for whole file

			String fileContent;
			while((fileContent = csvFile.readLine()) != null) {
				jsonContent = getJSONContent(fileContent,headers);
				jsonObjectList.add(jsonContent);
			}


			fileWriter.write(jsonObjectList.toJSONString());

			try {
				csvFile.close();
				fileWriter.flush();  
				fileWriter.close(); 
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}



	}

	private static ArrayList<String> retrieveHeaders(String headers) {
		String [] headersArray = headers.split(",");
		ArrayList<String> headerList = new ArrayList<String>();
		for(String header : headersArray) {
			headerList.add(header);
		}
		return headerList;
	}

	private static JSONObject getJSONContent(String fileContent, ArrayList<String> headers) {
		String [] fileContentValues= fileContent.split(",");
		JSONObject object = new JSONObject();
		for(int i = 0; i < headers.size(); i++) {
			object.put(headers.get(i), fileContentValues[i]);
		}
		return object;
	}
}

}
