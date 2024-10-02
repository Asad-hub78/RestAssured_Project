package ResAssuredProject3;

import java.util.HashMap;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ReqResTest {

	@Test
	public void GetAllEmployees() {
		RestAssured.given().baseUri("https://reqres.in/api/users")
		                   .when()
		                   .get()
		                   .then()
		                   .log()
		                   .all()
		                   .statusCode(200)
		                   .body("data.first_name[1]", Matchers.equalTo("Janet"));
	}

	@Test
	public void GetSingleEmployee() {
		RestAssured.given().baseUri("https://reqres.in/api/users")
		                   .when() 
		                   .get("/3")
		                   .then()
		                   .log()
		                   .all()
		                   .statusCode(200);
	}

	@Test
	public void PostEmployee() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "marpheus");
		map.put("Job", "leader");

		RestAssured.given().baseUri("https://reqres.in/api/users").contentType(ContentType.JSON)
				.accept(ContentType.JSON).body(map).when().post().then().log().all();

	}
}
