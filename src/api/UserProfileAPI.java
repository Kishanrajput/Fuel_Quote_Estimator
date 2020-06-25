package api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import service.UserProfileService;

@Path("user")
public class UserProfileAPI {
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Context HttpServletRequest httpServletRequest, String inputJSON) {

		try {
			JSONObject jsonObject = new JSONObject(inputJSON);
			String username = jsonObject.getString("username");
			String firstname = jsonObject.getString("firstname");
			String lastname = jsonObject.getString("lastname");
			String emailId  = jsonObject.getString("emailId");
			String phoneNumber = jsonObject.getString("phoneNumber");
			String address1 = jsonObject.getString("address1");
			String address2 =jsonObject.getString("address2");
			String state = jsonObject.getString("state");
			String city = jsonObject.getString("city");
			int zip = jsonObject.getInt("zip");	
			return UserProfileService.create(username, firstname, lastname, emailId, phoneNumber, address1, address2, state, city, zip);
		}catch (JSONException jsonException) {
			jsonException.printStackTrace();
			return Response.status(500).entity("Error occured while creating the JSON Object").build();
		}			
	}
	
	@POST
	@Path("populate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String populate(@Context HttpServletRequest httpServletRequest, String inputJSON) throws JSONException {
		
		try {
			JSONObject jsonObject = new JSONObject(inputJSON);
			String username = jsonObject.getString("username");
			
			return UserProfileService.populate(username);
		}catch (JSONException jsonException) {
			jsonException.printStackTrace();
			return new JSONObject().put("Error", "Error occured while creating the JSON Object").toString();
			//return Response.status(500).entity("Error occured while creating the JSON Object").build().toString();
		}
			
	}	
}

