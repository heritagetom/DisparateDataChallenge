package harvester.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import challenge.waterquality.DVHarvester;

public class test1 {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		//Test Database functionality
		DVHarvester testDatabase = new DVHarvester();
		testDatabase.addFormat("xml");
		String[] sites = {"01372040","01372300","03011020","03014500"};
		testDatabase.addSite(sites);
		String mainstring = testDatabase.getPath();
		System.out.println(testDatabase.getPath());
		
		String parentdir = "/Users/jacobtutmaher/Desktop/";
		File file = new File(parentdir+"test9.xml");
		URL url = new URL(mainstring);
		FileUtils.copyURLToFile(url,file);
		
	}

}
