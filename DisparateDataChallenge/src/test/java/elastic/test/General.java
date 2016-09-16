package elastic.test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class General {
	
	public static void main(String[] args) throws IOException{
		String pathToJson = "/Users/jacobtutmaher/Desktop/example3.json";
		//String pathToElastic = "/Users/jacobtutmaher/Desktop/elasticsearch";
		//String pathToKibana = "/Users/jacobtutmaher/Desktop/kibana";
		String index = "javatest";
		
		//Open Connection
		URL myUrl = new URL("http://localhost:9200/"+index);
		HttpURLConnection myURLConnection = (HttpURLConnection)myUrl.openConnection();
		myURLConnection.setRequestMethod("PUT");
		myURLConnection.setUseCaches(false);
		myURLConnection.setDoInput(true);
		myURLConnection.setDoOutput(true);
		myURLConnection.connect();
		
		//Get JSON Object
		String content = readFile(pathToJson, StandardCharsets.UTF_8);
		JSONObject json = new JSONObject(content);
		
		//Execute PUT
		OutputStream os = myURLConnection.getOutputStream();
		os.write(URLEncoder.encode(json.toString(),"UTF-8").getBytes());
		os.close();
	}
	
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
