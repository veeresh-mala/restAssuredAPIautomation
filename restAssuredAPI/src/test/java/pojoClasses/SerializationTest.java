package pojoClasses;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class SerializationTest {
	
	@Test
	public void addPlace() {
		RestAssured.baseURI ="https://rahulshettyacademy.com";
		AddPlace_Serialization ad = new AddPlace_Serialization();
		
		ad.setAccuracy(50);
		ad.setAddress("31, chilakaluri peta, chennai29");
		ad.setLanguage("Telugu-Te");
		ad.setPhone_number("9848911777");
		ad.setWebsite("https://rahulshettyacademy.com/");
		ad.setName("Vikram Singh Rathode");
		List<String> myList = new ArrayList<String>();
		myList.add("Shop");
		myList.add("Zoo Park");
		ad.setTypes(myList);
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		ad.setLocation(l);

		myList.add("Plant tree");
		myList.add("Shop");
			
		String response = given().log().all().queryParam("key", "qaclick123").
		body(ad).
		when().post("/maps/api/place/add/json").
		then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
	}
}
