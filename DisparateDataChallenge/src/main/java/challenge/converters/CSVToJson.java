package challenge.converters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CSVToJson {
	
	public ArrayList<JSONObject> convertFile(File f) {

		//Set up JSON objects
		ArrayList<JSONObject> jsonObjectList = new ArrayList<JSONObject>(); 
		JSONObject jsonContent = new JSONObject();

		try {

			//Read CSV File
			BufferedReader csvFile = new BufferedReader(new FileReader(f));
			String headerLine = csvFile.readLine();
			ArrayList<String> headers = retrieveHeaders(headerLine);

			//Get list of JSON objects for whole file

			String fileContent;
			while((fileContent = csvFile.readLine()) != null) {
				jsonContent = getJSONContent(fileContent,headers);
				jsonObjectList.add(jsonContent);
			}

			csvFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObjectList;
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
