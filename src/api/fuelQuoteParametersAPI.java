package api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import service.fuelQuoteParametersService;

@Path("param")
public class fuelQuoteParametersAPI {

	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String testService(@Context HttpServletRequest request) throws JSONException {
		return fuelQuoteParametersService.populate();
	}

	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Context HttpServletRequest httpServletRequest, String inputJSON) {
		try {
			JSONObject jsonObject;
			jsonObject = new JSONObject(inputJSON);
			
			double baseRate = jsonObject.getDouble("baserate");
			double profitMargin = jsonObject.getDouble("profitmargin");
			double fluctuationSummer = jsonObject.getDouble("fluctuationsummer");
			double fluctuationWinter = jsonObject.getDouble("fluctuationwinter");
			double transportationRatePerMile = jsonObject.getDouble("trpm");
			String startDate = jsonObject.getString("startdate");

			return fuelQuoteParametersService.update(baseRate, profitMargin, fluctuationSummer, fluctuationWinter,
					transportationRatePerMile, startDate);
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
			return Response.status(500).entity("Internal Server Error, while creating the JSON Object").build();
		} 
		
	}
}
