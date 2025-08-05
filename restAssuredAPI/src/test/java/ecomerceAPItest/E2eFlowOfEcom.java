package ecomerceAPItest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojoClasses.LoginEcom;
import pojoClasses.LoginResponseEcom;
import pojoClasses.Order;
import pojoClasses.OrderDetails;

public class E2eFlowOfEcom {

	
	// Log in 
	@Test
	public void login() {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginEcom le = new LoginEcom();
		le.setUserEmail("restassred@gmail.com");
		le.setUserPassword("Hello@123");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(le);

		LoginResponseEcom lre = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponseEcom.class);
		Reporter.log(lre.getToken(), true);
		String token = lre.getToken();
		Reporter.log(lre.getUserId(), true);
		String userId = lre.getUserId();
		Reporter.log(lre.getMessage(), true);
		
		// Add product
		RequestSpecification addProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		 
		RequestSpecification reqAddProduct = given().log().all().spec(addProduct).param("productName", "MacbookProM5")
		.param("productAddedBy", userId).param("productCategory", "Electronics")
		.param("productSubCategory", "Laptop").param("productPrice", "6999999")
		.param("productDescription", "macbook").param("productFor", "unisex")
		.multiPart("productImage", new File("C:\\Users\\LENOVO THINKPAD\\Downloads\\mac.jpg"));
		
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProductResponse);  
		String productId = js.get("productId");
		String code = js.get("statusCode");
		Reporter.log(code,true);
		
		// Create the order
		RequestSpecification createOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON).build();
		OrderDetails ods = new OrderDetails();
		ods.setCountry("india");
		ods.setProductOrderId(productId);
		
		List<OrderDetails> od = new ArrayList<>();
		od.add(ods);
		
		Order odr = new Order();
		odr.setOrder(od);
		
		RequestSpecification createOrderRes = given().log().all().spec(createOrderReq).body(odr);
		createOrderRes.when().post("/api/ecom/order/create-order").then().log().all().extract().response().toString();
		Reporter.log(addProductResponse,true);
		 
		
	}
}
