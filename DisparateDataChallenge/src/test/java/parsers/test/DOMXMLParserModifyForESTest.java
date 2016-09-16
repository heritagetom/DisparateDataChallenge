package parsers.test;

import java.io.File;

import javax.xml.transform.TransformerException;

import challenge.parsers.DOMXMLParser;

public class DOMXMLParserModifyForESTest {
	public static void main(String[] args) throws TransformerException
	{
		File fXmlFile = new File("/Users/thomasheritage/git/DisparateDataChallenge/DisparateDataChallenge/TestFiles/Other/WaterQuality/dv/CT2.xml");
		DOMXMLParser myParser = new DOMXMLParser(fXmlFile);
		myParser.modifyForES("/Users/thomasheritage/git/DisparateDataChallenge/DisparateDataChallenge/TestFiles/Other/WaterQuality/dv/CT3.xml");
	}
	

}
