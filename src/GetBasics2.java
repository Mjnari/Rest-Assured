import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.fge.jsonschema.examples.Utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import files.ReusableMethods;

public class GetBasics2 {
	
	Properties prop = new Properties();
	
	@BeforeTest
	public void getEnv() throws IOException {
		FileInputStream fis = new FileInputStream("src/files/env.properties");
		prop.load(fis);
	}

	@Test
	public void Test() {

		// Base url or host
		RestAssured.baseURI = prop.getProperty("HOST");

		// testing get
		Response res = given().param("location", "-33.8670522,151.1957362").param("radius", "500")
				.param("key", prop.getProperty("HOST_KEY")).log().all()
				.when().get("/maps/api/place/nearbysearch/json")
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body("results[0].name", equalTo("Sydney")).and()
				.body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and()
				.header("Server", "scaffolding on HTTPServer2").log().body().extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		int count = js.get("results.size()");
		for(int i = 0; i < count; i++) {
			System.out.println(js.get("results[" + i + "].name").toString());
		}

		// header, cookie, and body example
		// .header("headerKey","headerValue").cookie("key", "value").body("body");
	}

}
