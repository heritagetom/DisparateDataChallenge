package elastic.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class IndexTest {
	
	public static void main(String[] args) throws UnknownHostException{
		
		Client client = TransportClient.builder().build()
				   .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
	    
		String json = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";
		
		IndexResponse response = client.prepareIndex("twitter", "tweet").setSource(json).get();
		
		String index = response.getIndex();
		String type = response.getType();
		String id = response.getId();
		long version = response.getVersion();
		boolean created = response.isCreated();
		
		System.out.println("Index: "+index);
		System.out.println("Type: "+type);
		System.out.println("ID: "+id);
		System.out.println("Version: "+version);
		System.out.println("Created: "+created);
		
		client.close();
	}

}
