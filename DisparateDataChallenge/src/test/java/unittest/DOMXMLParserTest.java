package unittest;
import java.io.File;

import javax.swing.text.html.HTMLEditorKit.Parser;

import challenge.parsers.DOMXMLParser;

public class DOMXMLParserTest {
	public static void main(String[] args)
	{
		File fXmlFile = new File("/Users/thomasheritage/git/DisparateDataChallenge/DisparateDataChallenge/TestFiles/WaterQuality/dv/test.xml");
		DOMXMLParser myParser = new DOMXMLParser(fXmlFile);
		myParser.printValues();
	}
	
	
}
