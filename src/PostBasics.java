import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PostBasics {

	Properties prop = new Properties();

	@BeforeTest
	public void getEnv() throws IOException {
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\richm\\eclipse-workspace\\Rest-Assured1\\src\\files\\env.properties");
		prop.load(fis);
	}

	@Test
	public void Test() {

		// see notes for explanation on why the url and key are different
		RestAssured.baseURI = prop.getProperty("PROXY_HOST");

		// testing Post
		given().queryParam("key", "qaclick123")
				.body("{\r\n" + "\r\n" + "    \"location\":{\r\n" + "\r\n" + "        \"lat\" : -38.383494,\r\n"
						+ "\r\n" + "        \"lng\" : 33.427362\r\n" + "\r\n" + "    },\r\n" + "\r\n"
						+ "    \"accuracy\":50,\r\n" + "\r\n" + "    \"name\":\"Frontline house\",\r\n" + "\r\n"
						+ "    \"phone_number\":\"(+91) 983 893 3937\",\r\n" + "\r\n"
						+ "    \"address\" : \"29, side layout, cohen 09\",\r\n" + "\r\n"
						+ "    \"types\": [\"shoe park\",\"shop\"],\r\n" + "\r\n"
						+ "    \"website\" : \"http://google.com\",\r\n" + "\r\n"
						+ "    \"language\" : \"French-IN\"\r\n" + "\r\n" + "}\r\n" + "\r\n")
				.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK"));

	}

}
