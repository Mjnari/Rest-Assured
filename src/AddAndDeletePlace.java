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

import files.Resources;
import files.Payload;

public class AddAndDeletePlace {
	
	Properties prop = new Properties();

	@BeforeTest
	public void getEnv() throws IOException {
		FileInputStream fis = new FileInputStream("src/files/env.properties");
		prop.load(fis);
	}

	@Test
	public void AddandDeletePlace() {
		RestAssured.baseURI = prop.getProperty("PROXY_HOST");

		// add a place
		// grab the response so we can use the place_id to delete it
		Response res = given().queryParam("key", prop.getProperty("PROXY_HOST_KEY")).body(Payload.getPostData()).when().post(Resources.placePostData())
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

		given().queryParam("key", prop.getProperty("PROXY_HOST_KEY")).body(deleteBody).when().post("/maps/api/place/delete/json").then()
				.assertThat().statusCode(200).contentType(ContentType.JSON).and().body("status", equalTo("OK"));
	}
}
