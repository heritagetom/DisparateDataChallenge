package challenge.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;

import challenge.converters.CSVToJson;


public class IndexDocument {
	
	public void indexDocument(String path, String index, String subindex, String filePath) {
		try {
			String indexPath = createIndexPath(path,index,subindex);

			CSVToJson converter = new CSVToJson();
			File f = new File(filePath);
			ArrayList<JSONObject> json = converter.convertFile(f);

			for(int i = 0; i < json.size(); i++) {
				sendPostRequest(json.get(i).toJSONString(),indexPath);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String createIndexPath(String path, String index, String subindex) {
		String newPath = path+"/"+index+"/"+subindex;
		return newPath;
	}

	
	protected static void sendPostRequest(String json, String indexPath) {

		try {
			//Create Client
			CloseableHttpClient httpClient = HttpClients.createDefault();

			//Create POST request
			HttpPost request = new HttpPost(indexPath);
			StringEntity message = new StringEntity(json,"UTF-8");
			message.setContentType("application/json");
			request.setEntity(message);
			HttpResponse response = httpClient.execute(request);
			
			httpClient.close();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {
//
			e.printStackTrace();
		}
	}
}
