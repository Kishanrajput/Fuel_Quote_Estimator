package test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.AuthenticationAPI;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

class AuthenticationAPITest {

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	void testCreate() {
		String input = "{\"username\":\"testuser_\",\"password\":\"testpooja123\"}";
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals(200, api.create(httpServletRequest, input).getStatus());
	}

	@Test
	void When_DuplicateUsername_Expect_Error() {
		String input = "{\"username\":\"newuser1\",\"password\":\"testpooja123\"}";
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals("Username already Exists, Please try something else.", api.create(httpServletRequest, input).getEntity().toString());
	}

	@Test
	void When_InvalidInputToCreateMethod_Expect_Error() {
		String incorrectInput = "{\"username\":\"newuser1\",\"password\":\"testpooja123\"";
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals("Error occured while creating the JSON Object",
				api.create(httpServletRequest, incorrectInput).getEntity());
	}

	@Test
	void testAuthenticate() {

		String input = "{\"username\":\"testuser_6\",\"password\":\"testpooja123\"}";
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals(200, api.authenticate(httpServletRequest, input).getStatus());
	}
	
	@Test
	void testAdmin() {
		String input = "{\"username\":\"pooja\",\"password\":\"pooja\"}";
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals("isAdmin = 1", api.authenticate(httpServletRequest, input).getEntity());
	}
	
	@Test
	void testIncompleteProfile() {
		String input = "{\"username\":\"testuser_129\",\"password\":\"testpooja123\"}";
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals("ProfileComplete = 0", api.authenticate(httpServletRequest, input).getEntity());
	}

	@Test
	void When_InvalidInput_Expect_ErrorMessage() {
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectUsername = "{\"username\":\"newtest\",\"password\":\"testpooja123\"}";
		assertEquals("Incorrect username, please try again.", api.authenticate(httpServletRequest, incorrectUsername).getEntity());

		String incorrectPassword = "{\"username\":\"test\",\"password\":\"testblank\"}";
		assertEquals("Incorrect Password, please try again.", api.authenticate(httpServletRequest, incorrectPassword).getEntity());
	}

	@Test
	void When_IncorrectInput_Expect_Excpetion() {
		AuthenticationAPI api = new AuthenticationAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectInput = "{\"username\":\"newtest\",\"password\":\"testpooja123\"";
		assertEquals("Error occured while creating the JSON Object",
				api.authenticate(httpServletRequest, incorrectInput).getEntity());
	}

}
