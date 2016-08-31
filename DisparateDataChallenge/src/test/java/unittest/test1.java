package unittest;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import challenge.database.dv;

public class test1 {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		String parentdir = "/Users/jacobtutmaher/Desktop/";
		dv testDatabase = new dv();
		String mainstring = testDatabase.database();
		mainstring += testDatabase.addState("NY");
		mainstring += testDatabase.addSite(null);
		mainstring += testDatabase.addCounty(null);
		mainstring = mainstring.substring(0,mainstring.length()-1);
		System.out.println(mainstring);
		
		File file = new File(parentdir+"test.txt");
		URL url = new URL(mainstring);
		FileUtils.copyURLToFile(url,file);
		
	}

}
