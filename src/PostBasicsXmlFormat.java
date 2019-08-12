import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import files.Resources;
import files.Payload;
import files.ReusableMethods;

public class PostBasicsXmlFormat {

	Properties prop = new Properties();

	@BeforeTest
	public void getEnv() throws IOException {
		FileInputStream fis = new FileInputStream(
				"src/files/env.properties");
		prop.load(fis);
	}

	@Test
	public void Test() throws IOException {
		GenerateStringFromResource("src/files/postData.xml");
		

		// see notes for explanation on why the url and key are different
		String postData = RestAssured.baseURI = prop.getProperty("PROXY_HOST");

		// testing Post for xml
		// content type returned is still JSON
		Response res = given().queryParam("key", prop.getProperty("PROXY_HOST_KEY"))
				.body(postData)
				.when().post("/maps/api/place/add/xml").then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).extract().response();
		
		// in the course the google api was returning XML, but since we have to use this url, which returns an empty JSON, the below code does not work
		/*
		 *  XmlPath x = ReusableMethods.rawToXML(res);
		 *  System.out.println(x.get("PlaceAddResponse.place_id").toString());
		 */
	}
	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
