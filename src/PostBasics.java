import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import files.Resources;
import files.Payload;

public class PostBasics {

	Properties prop = new Properties();

	@BeforeTest
	public void getEnv() throws IOException {
		FileInputStream fis = new FileInputStream("src/files/env.properties");
		prop.load(fis);
	}

	@Test
	public void Test() {

		// see notes for explanation on why the url and key are different
		RestAssured.baseURI = prop.getProperty("PROXY_HOST");

		// testing Post
		given().queryParam("key", prop.getProperty("PROXY_HOST_KEY"))
				.body(Payload.getPostData())
				.when().post(Resources.placePostData()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK"));

	}

}
