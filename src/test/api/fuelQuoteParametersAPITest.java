package test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.UserProfileAPI;
import api.fuelQuoteParametersAPI;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class fuelQuoteParametersAPITest {
	
	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.defaultParser = Parser.JSON;
	}
	
	@Test
	void testCreate() {
		String input = "{\"baserate\":\"3.2\",\"profitmargin\":\"15\",\"fluctuationsummer\":\"10\",\"fluctuationwinter\":\"12\",\"trpm\":\"0.45\",\"startdate\":\"date\"}";
		fuelQuoteParametersAPI profile = new fuelQuoteParametersAPI();
		HttpServletRequest httpServletRequest = null;
		assertEquals(200, profile.create(httpServletRequest, input).getStatus());
	}
	
	@Test
	void testNull() {
		String input = "{\"baserate\":\"3.2\",\"profitmargin\":\"15\",\"fluctuationsummer\":\"10\",\"fluctuationwinter\":\"12\",\"trpm\":\"0.45\",\"startdate\":\"date\"}";
		fuelQuoteParametersAPI profile = new fuelQuoteParametersAPI();
		HttpServletRequest httpServletRequest = null;
		//assertEquals(200, profile.create(httpServletRequest, input).getStatus());
	}
	
	@Test
	void When_IncorrectInput_Expect_Excpetion() {
		UserProfileAPI profile = new UserProfileAPI();
		HttpServletRequest httpServletRequest = null;
		String incorrectInput = "{\"baserate\":\"3.2\",\"profitmargin\":\"15\",\"fluctuationsummer\":\"10\",\"fluctuationwinter\":\"12\",\"trpm\":\"0.45\",\"startdate\":\"date\"";
		assertEquals("Error occured while creating the JSON Object",
				profile.create(httpServletRequest, incorrectInput).getEntity());
	}

}
