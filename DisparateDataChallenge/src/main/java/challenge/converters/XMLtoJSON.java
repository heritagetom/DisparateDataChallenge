package challenge.converters;
import org.json.JSONObject;
import org.json.XML;
import java.io.*;

//This class accepts an xml file and converts it to JSON format, 
//which can be used for Elasticsearch

public class XMLtoJSON {
		public JSONObject convertFile(File file) throws Exception 
        {
			JSONObject jsonObj= new JSONObject();
            try
            {
                InputStream stream = new FileInputStream(file);
                StringBuilder sb =  new StringBuilder();
                //write xml source to a string builder object 
                int ptr = 0;
                while ((ptr = stream.read()) != -1 )
                {
                    sb.append((char) ptr);
                }
                stream.close();
                String xml  = sb.toString();
                //System.out.println(xml);
                //convert xml string to json object
                jsonObj = XML.toJSONObject(xml); 
                //System.out.println(jsonObj);
                return jsonObj;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return jsonObj;
        }

}
