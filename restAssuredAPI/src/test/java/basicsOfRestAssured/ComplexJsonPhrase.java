package basicsOfRestAssured;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import testData.PayLoad;

/* 1. Print No of courses returned by API

2.Print Purchase Amount

3. Print Title of the first course

4. Print All course titles and their respective Prices

5. Print no of copies sold by RPA Course

6. Verify if Sum of all Course prices matches with Purchase Amount */

public class ComplexJsonPhrase {

	int totalPurchaseAmout;

	@Test
	public void complexJsonPharse() {
		JsonPath jp = new JsonPath(new PayLoad().coursePrices());

		// Print the number of courses
		int CoursesCount = jp.getInt("courses.size()");
		// System.out.println(CoursesCount);
		Reporter.log("" + CoursesCount + "", true);

		// Print the purchase amount
		int purchaseAmount = jp.getInt("dashboard.purchaseAmount");
		Reporter.log("" + purchaseAmount + "", true);

		// Print the title of the course
		String courseTitle = jp.getString("courses[1].title");
		Reporter.log("" + courseTitle + "", true);

		// To print all titles and prices
		for (int i = 0; i < CoursesCount; i++) {

			String allCourseTitle = jp.get("courses[" + i + "].title");

			int priceOfAllCourses = jp.getInt("courses[" + i + "].price");

			// To print all courses title
			// Reporter.log(allCourseTitle, true);
			// Also you can write directly with out storing in variable
			Reporter.log(jp.getString("courses[" + i + "].title"), true);

			// To print all courses price
			Reporter.log("" + priceOfAllCourses + "", true);
			// Also you can write directly with out storing in variable
			Reporter.log("" + jp.getInt("courses[" + i + "].price") + "", true);

			// To print all courses price
			Reporter.log("" + jp.getInt("courses[" + i + "].copies") + "", true);

			// To get copies sold for the particular course
			if (allCourseTitle.equalsIgnoreCase("Cypress")) {
				Reporter.log("" + jp.getInt("courses[" + i + "].copies") + "", true);
			}

			// To get purchase amount of each course
			int eachCoursePurchaseAmount = jp.getInt("courses[" + i + "].price")
					* jp.getInt("courses[" + i + "].copies");
			Reporter.log("" + eachCoursePurchaseAmount + "", true);

			// To get total purchase amount of all courses
			totalPurchaseAmout = totalPurchaseAmout
					+ jp.getInt("courses[" + i + "].price") * jp.getInt("courses[" + i + "].copies");
			Reporter.log("" + totalPurchaseAmout + "", true);

		}

		Assert.assertEquals(purchaseAmount, totalPurchaseAmout);
		Reporter.log("purchaseAmount is equal to the totalPurchaseAmount", true);
	}

}