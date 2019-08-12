package files;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	
	public static XmlPath rawToXML(Response res) {
		String responseString = res.asString();
		XmlPath x = new XmlPath(responseString);
		return x;
	}
	
	public static JsonPath rawToJson(Response res) {
		String responseString = res.asString();
		JsonPath j = new JsonPath(responseString);
		return j;
	}

}
