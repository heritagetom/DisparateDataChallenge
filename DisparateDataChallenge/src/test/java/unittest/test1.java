package unittest;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import challenge.waterquality.dv;

public class test1 {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		//Test Database functionality
		dv testDatabase = new dv();
		testDatabase.addState("PA");
		testDatabase.addSite(null);
		testDatabase.addCounty(16350);
		System.out.println(testDatabase.getPath());
		
		//String parentdir = "/Users/jacobtutmaher/Desktop/";
		//File file = new File(parentdir+"test.txt");
		//URL url = new URL(mainstring);
		//FileUtils.copyURLToFile(url,file);
		
	}

}
