import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddAndDeletePlace {
	
	Properties prop = new Properties();

	@BeforeTest
	public void getEnv() throws IOException {
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\richm\\eclipse-workspace\\Rest-Assured1\\src\\files\\env.properties");
		prop.load(fis);
	}

	@Test
	public void AddandDeletePlace() {
		RestAssured.baseURI = prop.getProperty("PROXY_HOST");

		String addBody = "{\r\n" + "\r\n" + "    \"location\":{\r\n" + "\r\n" + "        \"lat\" : -38.383494,\r\n"
				+ "\r\n" + "        \"lng\" : 33.427362\r\n" + "\r\n" + "    },\r\n" + "\r\n"
				+ "    \"accuracy\":50,\r\n" + "\r\n" + "    \"name\":\"Frontline house\",\r\n" + "\r\n"
				+ "    \"phone_number\":\"(+91) 983 893 3937\",\r\n" + "\r\n"
				+ "    \"address\" : \"29, side layout, cohen 09\",\r\n" + "\r\n"
				+ "    \"types\": [\"shoe park\",\"shop\"],\r\n" + "\r\n"
				+ "    \"website\" : \"http://google.com\",\r\n" + "\r\n" + "    \"language\" : \"French-IN\"\r\n"
				+ "\r\n" + "}\r\n" + "\r\n";

		// add a place
		// grab the response so we can use the place_id to delete it
		Response res = given().queryParam("key", "qaclick123").body(addBody).when().post("/maps/api/place/add/json")
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body("status", equalTo("OK")).extract().response();

		// go from response to string
		String responseString = res.asString();
		System.out.println(responseString);
		// go from string to json
		JsonPath js = new JsonPath(responseString);

		String placeId = js.get("place_id");
		System.out.println(placeId);

		String deleteBody = "{ \"place_id\": \"" + placeId + "\"}";

		given().queryParam("key", "qaclick123").body(deleteBody).when().post("/maps/api/place/delete/json").then()
				.assertThat().statusCode(200).contentType(ContentType.JSON).and().body("status", equalTo("OK"));
	}
}
