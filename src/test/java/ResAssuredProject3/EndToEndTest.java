package ResAssuredProject3;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import groovyjarjarantlr.collections.List;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {
	String emp_id;

	@Test
	public void TC01_GetAllEmployee() {
		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String responseBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(responseBody);
		System.out.println(resCode);
		Assert.assertEquals(resCode, 200);

	}

	@Test
	public void TC02_GetSingleEmployee() {
		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();
		Response response = request.get("/1");
		String responseBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(responseBody);
		System.out.println(resCode);
		Assert.assertEquals(resCode, 200);
		JsonPath json = response.jsonPath();
		String employee_name = json.get("name");
		System.out.println(employee_name);

	}

	@Test
	public void TC03_CreateEmployee() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Rakesh");
		map.put("salary", "7000");
		map.put("id", "100");

		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(map).post();
		String responseBody = response.getBody().asString();
		int responseCode = response.getStatusCode();
		System.out.println(responseBody);
		System.out.println(responseCode);
		Assert.assertEquals(responseCode, 201);
		JsonPath path = response.jsonPath();
		emp_id = path.get("id");
		System.out.println("The Emloyee Id is " + emp_id);
	}

	@Test
	public void TC04_DeleteEmployee() {
		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();
		Response response = request.delete(emp_id);
		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(resBody);
		System.out.println(resCode);
		Assert.assertEquals(resCode, 200);

	}

	@Test
	public void TC05_TestDeletedEmployee() {
		RestAssured.baseURI = "http://localhost:3000/employees/" + emp_id;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String responseBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(responseBody);
		System.out.println(resCode);
		Assert.assertEquals(resCode, 404);
	}
}
