package basicsOfRestAssured;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import testData.PayLoad;

public class JiraReportBug {

	@Test
	public void createBug() {
		RestAssured.baseURI="https://malaveeresh1998.atlassian.net";
		
	String createBugResponse =
		given()
		.header("Authorization", "Basic bWFsYXZlZXJlc2gxOTk4QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBlSVVlOV9xT1h0R0hmSmVOTDRfaXFJUVI2ektUZ1ByOGdVb1NKMVRyQTdUbG5DaE9YOERDS1p2R2V1X3JIQi1HRTN0cFBsLXF5MGd6QkYxdlNvaFhnRkdJV09NWGJ6Q3N6dUtVSTFSX21uaTJYWHVhWGJZbDFSblQ2Y0xXanl0a00zTUphTXR6eHJaeEYxVjljb21td0ZRMXhBZG05U21GLTI5ZkNjeVRwMXM9NUI2OTNFMkE=")
		.header("Content-Type","application/json")
		.body(new PayLoad().createJiraBug("Kishore getting instead of 700"))
		
		.when().post("rest/api/2/issue")
		
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
	
	JsonPath path = new JsonPath(createBugResponse);
	String issueID = path.getString("id");
	Reporter.log(issueID,true);
	
	//add attachment
	given().pathParam("key", issueID)
	.header("Authorization", "Basic bWFsYXZlZXJlc2gxOTk4QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBlSVVlOV9xT1h0R0hmSmVOTDRfaXFJUVI2ektUZ1ByOGdVb1NKMVRyQTdUbG5DaE9YOERDS1p2R2V1X3JIQi1HRTN0cFBsLXF5MGd6QkYxdlNvaFhnRkdJV09NWGJ6Q3N6dUtVSTFSX21uaTJYWHVhWGJZbDFSblQ2Y0xXanl0a00zTUphTXR6eHJaeEYxVjljb21td0ZRMXhBZG05U21GLTI5ZkNjeVRwMXM9NUI2OTNFMkE=")
//	.header("Content-Type","application/json")
	.header("X-Atlassian-Token","no-check")
	
	//passing attachment here
	//.multiPart("file", new File("\"C:\\Users\\LENOVO THINKPAD\\eclipse-workspace\\automationFrameWork\\errorShots\\toCreateContact_001-Thu Apr 03 10-07-09 2025.png\""))
	.multiPart("file", new File("C:\\Users\\LENOVO THINKPAD\\eclipse-workspace\\automationFrameWork\\errorShots\\toCreateContact_001-Thu Apr 03 10-07-09 2025.png"))

	//When i hit URL with post https method 
	.when().post("/rest/api/3/issue/{key}/attachments")
	
	//Then i should get status code as 200
	.then().log().all().assertThat().statusCode(200);
	}
}
