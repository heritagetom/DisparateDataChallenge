package challenge.main;


import java.io.IOException;
import java.util.ArrayList;


import challenge.gui.MainFrame;
import challenge.index.IndexDocument;

public class MainApplication {
	private String kibanaPath;
	private String elasticSearchPath;

	public static void main(String[] args) {

		runElasticSearch("C:\\kibana-4.6.1\\kibana-4.6.1-windows-x86\\bin\\kibana.bat");
	}


	public String getKibanaPath() {
		return kibanaPath;
	}


	public void setKibanaPath(String kibanaPath) {
		this.kibanaPath = kibanaPath;
	}


	public String getElasticSearchPath() {
		return elasticSearchPath;
	}


	public void setElasticSearchPath(String elasticSearchPath) {
		this.elasticSearchPath = elasticSearchPath;
	}


	public void run(String database, String dataset, String dataLink) {

		//Set index path 
		String type = dataLink.toLowerCase().replaceAll("\\s+","");
		String ind = database.toLowerCase().replaceAll("\\s+","");
		String typeFileName = dataLink.replaceAll("\\s+", "");
		String indFileName = database.replaceAll("\\s", "");
		String fileName = "src/main/resources/"+ indFileName + "/" + typeFileName + "/" + dataset;

		//Launch elasticsearch
		//runElasticSearch("C:\\elasticsearch-2.4.0\\elasticsearch-2.4.0\\bin\\elasticsearch.bat");

		//Wait for elasticsearch to start
		//sleep();
		
		
		//Launch Kibana
		//runElasticSearch("C:\\kibana-4.6.1\\kibana-4.6.1-windows-x86\\bin\\kibana.bat");

		//Wait for Kibana to run
		//sleep();
		
		//Store document into elasticsearch

		//for(int i = 0; i < typeList.size(); i++) {

		IndexDocument store = new IndexDocument();
		store.indexDocument("http://localhost:9200",ind,type,fileName);
		System.out.println("stored");
		//}



	}

	public static void runElasticSearch(String path) {
		try{    
			Process p = Runtime.getRuntime().exec("cmd.exe /k start" + path);
			p.waitFor();

		}catch( Exception e ){
			e.printStackTrace();
		}
	}

	public void sleep() {
		try {
			Thread.sleep(10000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
