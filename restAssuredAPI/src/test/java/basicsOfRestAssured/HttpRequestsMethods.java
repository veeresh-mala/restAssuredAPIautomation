package basicsOfRestAssured;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import testData.PayLoad;

public class HttpRequestsMethods {
	
	@Test
	public void httpMethodsPost() { // To create data
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String respons = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new PayLoad().bodyOfPostMethod())
				.when().post("/maps/api/place/add/json")			
				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		         Reporter.log(respons,true);
		
		JsonPath path = new JsonPath(respons);
		String place_ID = path.getString("place_id");
        Reporter.log(place_ID,true);

	//	System.out.println(place_ID);
		
		String newAddress = "Vizag RK, India";
		  
		//We use here PUT method to update data
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"place_id\": \""+place_ID+"\",\r\n"
				+ "    \"address\": \""+newAddress+"\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
	    //We use Get method to retrieve data 
	String updatedAddress = given().log().all().queryParam("key", "qaclick123")
	    .queryParam("place_id", place_ID)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
         Reporter.log(updatedAddress,true);
         
     	JsonPath js = new JsonPath(updatedAddress);
     	String actualAddress = js.getString("address"); 
     	Assert.assertEquals(newAddress, actualAddress);
	}	
}
