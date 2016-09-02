package parsers.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import challenge.parsers.CSV;

public class CSVtest {
	public static void main(String[] args) throws IOException{
		String path = "TestFiles/HumanitarianData/DataForWorldFoodProgram/CSV/example.csv";
		CSV csvobj = new CSV(path);
		Map<String,List<String>> hmap = csvobj.getMap();
		System.out.println(hmap.keySet());
		System.out.println(hmap.size());
		System.out.print(hmap.get("mp_year").get(0)+" ");
		System.out.print(hmap.get("um_id").get(0)+" ");
		System.out.print(hmap.get("adm0_name").get(0)+" ");
		System.out.print(hmap.get("adm0_id").get(0)+" ");
		System.out.print(hmap.get("adm1_name").get(0)+" ");
		System.out.print(hmap.get("adm1_id").get(0)+" ");
	}
}
