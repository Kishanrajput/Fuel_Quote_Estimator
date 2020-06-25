package test.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import api.FuelQuoteClientAPI;
import service.FuelQuoteClientService;

class FuelQuoteClientAPITest {
	
	FuelQuoteClientAPI fuelQuoteClientAPI = new FuelQuoteClientAPI();
	HttpServletRequest httpServletRequest = null;

	@Test
	void testCreate() throws JSONException {
		String input = "{\"ngallons\":14," + 
				"\"ddate\":\"2019-02-12\"," + 
				"\"price\":2.30," + 
				"\"discount\":.02," + 
				"\"transportation\":.08," + 
				"\"finalprice\":120.25," + 
				"\"total\":100.25," + 
				"\"username\":\"testuser_6\"," + 
				"}";
		JSONObject jsonObject = new JSONObject(fuelQuoteClientAPI.create(httpServletRequest, input));
		assertEquals("Your Order is placed successfully. Fuel will be delivered in 3 business days.", jsonObject.getString("message"));
	}

	@Test
	void When_IncorrectJSON_Expect_Errorincreate() throws JSONException {
		String input = "{\"ngallons\":14," + 
				"\"ddate\":\"2019-12-10\"," + 
				"\"price\":2.30," + 
				"\"discount\":.02," + 
				"\"transportation\":.08," + 
				"\"finalprice\":120.25," + 
				"\"total\":100.25," + 
				"\"username\":\"testuser_6\"," + 
				"";
		
		JSONObject response = new JSONObject(fuelQuoteClientAPI.create(httpServletRequest, input));
		assertEquals("Error occured while creating the JSON Object", response.getString("message"));	
		//assertEquals(500, response.getStatus());
	}
	
	@Test 
	void When_NullData_Expect_ErrorInCreate() throws JSONException{
		String input = "{\"ngallons\":14," + 
				"\"ddate\":\"\"," + 
				"\"price\":2.30," + 
				"\"discount\":.02," + 
				"\"transportation\":.08," + 
				"\"finalprice\":140," + 
				"\"total\":100.25," + 
				"\"username\":\"testuser_6\"," + 
				"}";
		
		JSONObject response = new JSONObject(fuelQuoteClientAPI.create(httpServletRequest, input));
		assertEquals("Incomplete data", response.getString("message"));
		//assertEquals(400, response.getString());
	}
	
	@Test
	void testFetchAll() {
		assertNotNull(fuelQuoteClientAPI.fetchAll(httpServletRequest, null, null, null));
		assertNotNull(fuelQuoteClientAPI.fetchAll(httpServletRequest, "testuser_6", null, null));
		assertEquals(30, fuelQuoteClientAPI.fetchAll(httpServletRequest, "testuser_6", "01/12/2019", "01/21/2019").size());
		
	}

	@Test
	void When_IncorrectInput_Expect_Emptylist() {
		assertEquals(0, fuelQuoteClientAPI.fetchAll(httpServletRequest, "test18", null, null).size());
	}
	
	@Test
	void testConvertStringToDate() {
		assertEquals(new Date("02/12/2019"), FuelQuoteClientService.convertStringToDate("2019-02-12"));
		assertNull(FuelQuoteClientService.convertStringToDate("-11-2018"));
	}
	
	@Test
	void testConvertDateToString() {
		assertEquals("2018-11-11", FuelQuoteClientService.convertDateToString(new Date("11/11/2018")));
	}
	
	@Test
	void testCalculate() throws JSONException 
	{
		/*String input  = "{\"username\":\"test\", "+
				"\"ngallons\":14,"+
				"\"ddate\":\"2019-12-10\","+ 
				"}";*/
		String input = "{\"username\":\"test\", \"ngallons\":\"1\",\"ddate\": \"2019-12-10\"}";
			
		JSONObject jsonObject = new JSONObject(fuelQuoteClientAPI.calculate(httpServletRequest, input));
		assertEquals("No Error", jsonObject.getString("Error"));
		
	}

@Test
	void When_IncorrectInput_Expect_Excpetion_in_Calculate() throws JSONException {
		String input  = "{\"username\":\"test\", "
				+ "\"ngallons\":14,"
				+ "}";	
		
		assertEquals("Error occured while creating the JSON Object",
				new JSONObject(fuelQuoteClientAPI.calculate(httpServletRequest, input)).get("Error"));

	}
	
	@Test
	void When_Incomplete_data__Calculate() throws JSONException {
		String incorrectInput = "{\"username\":\"test\", \"ngallons\":\"0\",\"ddate\": \"2019-12-10\"}";
					
		
		//String input = "{\"username\":\"test\", \"firstname\":\"meenakshi\",\"lastname\":\"kumar\",\"emailId\":\"akumar@uh.edu\",\"phoneNumber\":\"12\",\"address1\":\"fa\",\"address2\":\"st\",\"state\":\"texas\",\"city\":\"houston\",\"zip\":\"0\"}";
		assertEquals("Incomplete data",
				new JSONObject(fuelQuoteClientAPI.calculate(httpServletRequest, incorrectInput)).get("Error"));
	}
}
