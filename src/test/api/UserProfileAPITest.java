package test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.AuthenticationAPI;
import api.UserProfileAPI;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class UserProfileAPITest {

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	void testCreate() {
		String input = "{\"username\":\"test\", \"firstname\":\"Meenakshi\",\"lastname\":\"kumar\",\"emailId\":\"akumar@uh.edu\",\"phoneNumber\":\"12\",\"address1\":\"fa\",\"address2\":\"st\",\"state\":\"texas\",\"city\":\"houston\",\"zip\":\"123\"}";
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals(200, profile.create(httpServletRequest, input).getStatus());
	}

	@Test
	void testStatus() {
		String input = "{\"username\":\"test\", \"firstname\":\"meenakshi\",\"lastname\":\"kumar\",\"emailId\":\"akumar@uh.edu\",\"phoneNumber\":\"12\",\"address1\":\"fa\",\"address2\":\"st\",\"state\":\"texas\",\"city\":\"houston\",\"zip\":\"0\"}";
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals(200, profile.create(httpServletRequest, input).getStatus());
	}

	@Test
	void testEntity() {
		String input = "{\"username\":\"test\", \"firstname\":\"\",\"lastname\":\"kumar\",\"emailId\":\"akumar@uh.edu\",\"phoneNumber\":\"12\",\"address1\":\"fa\",\"address2\":\"st\",\"state\":\"texas\",\"city\":\"houston\",\"zip\":\"12\"}";
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals("There is some error in your data, Please fill the details carefully.", profile.create(httpServletRequest, input).getEntity());
	}

	@Test
	void When_IncorrectInput_Expect_Excpetion() {
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectInput = "{\"username\":\"test\", \"firstname\":\"meenakshi\",\"lastname\":\"kumar\",\"emailId\":\"akumar@uh.edu\",\"phoneNumber\":\"12\",\"address1\":\"fa\",\"address2\":\"st\",\"state\":\"texas\",\"city\":\"houston\",\"zip\":\"0\"";
		assertEquals("Error occured while creating the JSON Object",
				profile.create(httpServletRequest, incorrectInput).getEntity());
	}

	@Test
	void Test_Populate() throws JSONException {
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectInput = "{\"username\":\"test\"}";
		assertEquals("No Error",
				new JSONObject(profile.populate(httpServletRequest, incorrectInput)).get("Error"));

	}
	
	@Test
	void Test_Incomplete_Profile() throws JSONException {
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectInput = "{\"username\":\"newuser1\"}";
		assertEquals("NoProfile",
				new JSONObject(profile.populate(httpServletRequest, incorrectInput)).get("Error"));
	}
	@Test
	void When_IncorrectInput_Expect_Excpetion_in_Populate() throws JSONException {
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectInput = "{\"username\":\"test\"";
		assertEquals("Error occured while creating the JSON Object",
				new JSONObject(profile.populate(httpServletRequest, incorrectInput)).get("Error"));

	}
	
	@Test
	void When_Incorrect_Username__Populate() throws JSONException {
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectInput = "{\"username\":\"\"}";
		assertEquals("Access Denied",
				new JSONObject(profile.populate(httpServletRequest, incorrectInput)).get("Error"));

	}
	
	
}
