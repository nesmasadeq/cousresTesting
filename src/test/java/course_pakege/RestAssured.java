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
	
	//public global string to save token
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
	
	@Test (priority=1)
	public void getAllCoursesTC() {
		System.out.println("token->"+TOKEN);
		baseURI = "http://localhost:3000/660";
		RequestSpecification request=given();
		request.header("Content-type","application/json");
		request.header("Authorization", "Bearer "+ TOKEN);
		Response response= request.get("/courses");
		System.out.println("body "+response.body().asPrettyString());
		//BDD
		response.then().statusCode(200).body("[2].id",equalTo(3)).log().all();
	}
	
	@Test (priority=2)
	public void postCourseTC() {
		baseURI = "http://localhost:3000/660";
		JSONObject requestParameter= new JSONObject();
		requestParameter.put("id", 7);
		requestParameter.put("name", "Test rest");
		requestParameter.put("describtion", "Testing course");
		requestParameter.put("author", "Nesma test");
		RequestSpecification request=given();
		request.header("Content-type","application/json");
		request.header("Authorization", "Bearer "+ TOKEN);
		request.body(requestParameter.toString());
		Response response = request.post("/courses");
		int statusCode=response.getStatusCode();
		AssertJUnit.assertEquals(statusCode, 201);

	}
	
	@Test (priority=3)
	public void editCourseTC() {
		baseURI = "http://localhost:3000/660";
		JSONObject requestParameter= new JSONObject();
		requestParameter.put("name", "Test rest2");
		RequestSpecification request=given();
		request.header("Content-type","application/json");
		request.header("Authorization", "Bearer "+ TOKEN);
		request.body(requestParameter.toString());
		Response response = request.patch("/courses/7");
		int statusCode=response.getStatusCode();
		AssertJUnit.assertEquals(statusCode, 200);	
	}
	
	@Test (priority=4)
	public void getCourseTC() {
		baseURI = "http://localhost:3000/660";
		RequestSpecification request=given();
		request.header("Content-type","application/json");
		request.header("Authorization", "Bearer "+ TOKEN);
		Response response = request.get("/courses/7");
		int statusCode=response.getStatusCode();
		AssertJUnit.assertEquals(statusCode, 200);
		String name= response.jsonPath().get("name");
		AssertJUnit.assertEquals(name, "Test rest2");
	}
	
	@Test (priority=5)
	public void deleteCourseTC() {
		baseURI = "http://localhost:3000/660";
		RequestSpecification request=given();
		request.header("Content-type","application/json");
		request.header("Authorization", "Bearer "+ TOKEN);
		Response response = request.delete("/courses/7");
		int statusCode=response.getStatusCode();
		AssertJUnit.assertEquals(statusCode, 200);
	}
	
}
