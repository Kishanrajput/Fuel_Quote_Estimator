package api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import service.AuthenticationService;

@Path("auth")
public class AuthenticationAPI {
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Context HttpServletRequest httpServletRequest, String inputJSON) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(inputJSON);
			String username = jsonObject.getString("username");
			String password = jsonObject.getString("password");
			return AuthenticationService.create(username, password);
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
			return Response.status(500).entity("Error occured while creating the JSON Object").build();
		}
	}

	@POST
	@Path("validate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate(@Context HttpServletRequest httpServletRequest, String inputJSON) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(inputJSON);
			return AuthenticationService.authenticate(jsonObject.getString("username"),
					jsonObject.getString("password"));
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
			return Response.status(500).entity("Error occured while creating the JSON Object").build();
		}
	}
}
