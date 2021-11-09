package course_pakege;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssured {

	public String TOKEN;
	 
	@Test
	public void register() {
		JSONObject requestParameter= new JSONObject();
		requestParameter.put("email", "olivier@mail.com");
		requestParameter.put("password", "bestPassw0rd");
		baseURI = "http://localhost:3000";
		RequestSpecification request=given();
		request.header("Content-type","application/json");
		request.body(requestParameter.toString());
		Response response = request.post("/register");
		int statusCode=response.getStatusCode();
		AssertJUnit.assertEquals(statusCode, 201);

	}
	
	@BeforeTest 
	public void login() {
		JSONObject requestParameter= new JSONObject();
		requestParameter.put("email", "olivier@mail.com");
		requestParameter.put("password", "bestPassw0rd");
		baseURI = "http://localhost:3000";
		RequestSpecification request=given();
		request.header("Content-type","application/json");
		request.body(requestParameter.toString());
		Response response = request.post("/login");
		int statusCode=response.getStatusCode();
		System.out.println("code->"+statusCode);
		AssertJUnit.assertEquals(statusCode, 200);
		String email= response.jsonPath().get("user.email");
		AssertJUnit.assertEquals(email, "olivier@mail.com");
		TOKEN= response.jsonPath().get("accessToken");

	}
	
	
}
