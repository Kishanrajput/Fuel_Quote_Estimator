package api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import entity.FuelQuoteClient;
import service.FuelQuoteClientService;

@Path("quote")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuelQuoteClientAPI {

	@POST
	@Path("save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String create(@Context HttpServletRequest httpServletRequest, String inputJSON) throws JSONException {

		JSONObject jsonObject;

		try {

			jsonObject = new JSONObject(inputJSON);
			int numberOfGallons = Integer.parseInt(jsonObject.getString("ngallons"));
			String deliveryDate = jsonObject.getString("ddate");
			double perGallon = Double.parseDouble(jsonObject.getString("price"));
			double discount = Double.parseDouble(jsonObject.getString("discount"));
			double transportation = Double.parseDouble(jsonObject.getString("transportation"));
			double finalprice = Double.parseDouble(jsonObject.getString("finalprice"));
			double totalAmount = Double.parseDouble(jsonObject.getString("total"));
			String username = jsonObject.getString("username");
			return FuelQuoteClientService.create(numberOfGallons, deliveryDate, perGallon, discount, transportation,
					finalprice, totalAmount, username);
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
			JSONObject response = new JSONObject();
			response.put("message", "Error occured while creating the JSON Object");
			return response.toString();
		}
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FuelQuoteClient> fetchAll(@Context HttpServletRequest httpServletRequest,
			@QueryParam("username") String username, @QueryParam("fromdate") String startDate,
			@QueryParam("todate") String endDate) {
		List<FuelQuoteClient> clients = FuelQuoteClientService.fetchAll(username, startDate, endDate);
		return clients;
	}

	@POST
	@Path("getQuote")
	@Consumes(MediaType.APPLICATION_JSON)
	public String calculate(@Context HttpServletRequest httpServletRequest, String inputJSON) throws JSONException {
		try {
			JSONObject jsonObject;
			jsonObject = new JSONObject(inputJSON);
			String username = jsonObject.getString("username");
			String ngallons = jsonObject.getString("ngallons");
			String date = jsonObject.getString("ddate");
			return FuelQuoteClientService.calculate(username, ngallons, date);
		}
		catch (JSONException jsonException) {
			jsonException.printStackTrace();
			return new JSONObject().put("Error", "Error occured while creating the JSON Object").toString();
			
		}
	}

}
