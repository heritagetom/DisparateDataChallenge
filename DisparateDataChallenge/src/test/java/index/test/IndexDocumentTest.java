package index.test;


import challenge.index.IndexDocument;


public class IndexDocumentTest {
//
	public static void main(String[] args) {

		IndexDocument store = new IndexDocument();
		store.indexDocument("http://localhost:9200","ex","test","TestFiles/HumanitarianData/WestAfricanEbolaOutbreak/CSV/example.csv");

	}
	

}
