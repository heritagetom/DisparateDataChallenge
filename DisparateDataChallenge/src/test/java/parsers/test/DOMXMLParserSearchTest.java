package parsers.test;
import java.io.File;
import challenge.parsers.*;

public class DOMXMLParserSearchTest 
{
	public static void main(String[] args)
	{
		File fXmlFile = new File("/Users/thomasheritage/git/DisparateDataChallenge/DisparateDataChallenge/TestFiles/Other/WaterQuality/dv/test1.xml");
		DOMXMLParser dp = new DOMXMLParser(fXmlFile);
		System.out.println(dp.searchFor("ns1:siteName"));
	}

}
