import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.ReusableMethods;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExcelDriven {
	
	Properties prop = new Properties();
	
	@BeforeTest
	public void getEnv() throws IOException {
		FileInputStream fis = new FileInputStream("src/files/env.properties");
		prop.load(fis);
	}

	@Test
	public void addBook() {

		// Base url or host
		RestAssured.baseURI = prop.getProperty("PROXY_HOST");

		// testing get
		Response res = given()
				.header("Content-Type", "application/json").body("{\n" + 
						"\n" + 
						"\"name\":\"Learn Appium Automation with Java\",\n" + 
						"\"isbn\":\"bcssdqqzdasdffdsafasadsfas\",\n" + 
						"\"aisle\":\"227\",\n" + 
						"\"author\":\"John foe\"\n" + 
						"}\n" + 
						"")
				.when().post("/Library/Addbook.php")
				.then().assertThat().statusCode(200).extract().response();

		JsonPath js = ReusableMethods.rawToJson(res);
		System.out.println(js.get("ID").toString());
	}

}
