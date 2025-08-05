package basicsOfRestAssured;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import pojoClasses.Api;
import pojoClasses.WebAutomation;
import pojoClasses.getCourses;
public class OauthAutomation {
	
	@Test
	public void oAuthClientGrantaType() {
		
		String[] courses = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		String response = given()
		.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W" )
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust").log().all()
		
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
	     Reporter.log(response,true);
	     
	     JsonPath js = new JsonPath(response);
	     Reporter.log(""+js.getString("access_token")+"",true);
	     
	     getCourses gc =  given()
	     .queryParam("access_token", ""+js.getString("access_token")+"")
	     .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(getCourses.class);
	     
	     // System.out.println(ac.getInstructor());
	     // System.out.println(ac.getLinkedIn());
	     
	     Reporter.log(gc.getInstructor(), true);
	     Reporter.log(gc.getLinkedIn(), true);
	     
	     gc.getCourses().getApi().get(0).getCourseTitle();
	     
	     List<Api> allAPIcourses = gc.getCourses().getApi();
	     
	     for(int i = 0; i < allAPIcourses.size(); i++ ) {
	    	 if(allAPIcourses.get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
	    		 Reporter.log(allAPIcourses.get(i).getPrice(), true);
	    	 }
	     }	
	     
	     List<WebAutomation> allWebACourses = gc.getCourses().getWebAutomation();
	     ArrayList<String> a = new ArrayList<String>();
	     
	     for(int i =0; i < allWebACourses.size(); i++) {
	    	a.add(allWebACourses.get(i).getCourseTitle());
	     }
	     
	     List<String> expectedCourses =   Arrays.asList(courses);
	     Assert.assertTrue(a.equals(expectedCourses));
	}	
}
