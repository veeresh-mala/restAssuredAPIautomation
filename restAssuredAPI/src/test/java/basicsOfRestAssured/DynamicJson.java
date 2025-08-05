package basicsOfRestAssured;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import testData.PayLoad;

public class DynamicJson {

	@Test(dataProvider = "booksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";

		String response = 
				given().log().all().headers("Content-Type", "application/json").body(new PayLoad().addBook(isbn,aisle))
				.when().post("Library/Addbook.php")
				.then().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(response);
		String id = js.getString("ID");
		Reporter.log(id, true);
	}
	
	@DataProvider(name="booksData")
	public String[][] getData() {
		String booksData[][] = {
				{"bcd","1123"},
				{"cde","2234"},
				{"def","3345"}
				};
		return booksData;
	}

}
