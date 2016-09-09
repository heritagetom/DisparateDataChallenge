package converters.test;
import java.io.*;
import challenge.converters.*;


public class XMLtoJSONTest {
	public static void main(String[] args) throws Exception
	{
		try{
		File file = new File("TestFiles/Other/WaterQuality/dv/test1.xml");
		XMLtoJSON testConverter = new XMLtoJSON();
		System.out.println(testConverter.convertFile(file));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
