package index.test;


import challenge.index.IndexDocument;


public class IndexDocumentTest {
//
	public static void main(String[] args) {

		IndexDocument store = new IndexDocument();
		store.indexDocument("http://localhost:9200","wfp","ecuadorroadaccess","TestFiles/Other/GEOJSON/ecuadorroadaccess.json");

	}
	

}
