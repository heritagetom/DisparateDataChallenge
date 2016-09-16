package challenge.main;


import java.io.IOException;
import java.util.ArrayList;


import challenge.gui.MainFrame;
import challenge.index.IndexDocument;

public class MainApplication {



	public static void main(String[] args) {
		MainApplication mainApplication = new MainApplication();
		MainFrame mainFrame = new MainFrame();
		mainApplication.run(mainFrame);
	}


	public void run(MainFrame mainFrame) {
		
		String type = mainFrame.getDataLinkName();
		String ind = mainFrame.getDataBaseName();
		String fileName = "src/main/resources/"+ type.trim() + "/" + ind.trim() + "/" + mainFrame.getDataSetName();
		
			
		runElasticSearch("C:\\elasticsearch-2.4.0\\elasticsearch-2.4.0\\bin\\elasticsearch.bat");

		//for(int i = 0; i < typeList.size(); i++) {
			IndexDocument store = new IndexDocument();
			store.indexDocument("http://localhost:9200",ind,type,fileName);
		//}

		runElasticSearch("C:\\kibana-4.6.1\\kibana-4.6.1\\bin\\elasticsearch.bat");
	}

	public static void runElasticSearch(String path) {
		try{    
			Process p = Runtime.getRuntime().exec("cmd.exe /k start " + path);
			p.waitFor();

		}catch( Exception e ){
			e.printStackTrace();
		}
	}



}
