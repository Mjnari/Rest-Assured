import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

	@Test
	public void Test() {

		// Base url or host
		RestAssured.baseURI = "https://maps.googleapis.com";

		given().param("location", "-33.8670522,151.1957362").param("radius", "500")
				.param("key", "AIzaSyAPtvrphF0bR2Wa4sEReomaCUzKpkYXdHI").when().get("/maps/api/place/nearbysearch/json")
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body("results[0].name", equalTo("Sydney")).and()
				.body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and()
				.header("Server", "scaffolding on HTTPServer2");

		System.out.println("work");

		// header and body example
		// .header("headerKey","headerValue").cookie("key", "value").body("body");
	}

}
