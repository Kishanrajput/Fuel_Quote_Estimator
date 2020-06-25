/* */
package service;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import dao.AuthenticationDataManager;
import dao.fuelQuoteParametersDataManager;
import entity.UserCredential;
import entity.fuelQuoteParameters;
import exception.AppException;

public class fuelQuoteParametersService {

	public static Response update(double baseRate, double profitMargin, double fluctuationSummer,
			double fluctuationWinter, double trpm, String startDate) throws JSONException {
		//JSONObject response = new JSONObject();
		String entity;
		int statusCode;
		try {
			fuelQuoteParameters fuelquoteparams = new fuelQuoteParameters();
			fuelquoteparams.setBaseRate(baseRate);
			fuelquoteparams.setProfitMargin(profitMargin);
			fuelquoteparams.setFluctuationSummer(fluctuationSummer);
			fuelquoteparams.setFluctuationWinter(fluctuationWinter);
			fuelquoteparams.setTransportationRatePerMile(trpm);
			fuelquoteparams.setStartDate(startDate);
			// Data manger method
			fuelQuoteParametersDataManager.update(fuelquoteparams);

			statusCode = HttpStatus.SC_OK;
			entity = "Parameters updated successfully.";
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			entity = "Error occured while updating the parameters.";
		}
		
		return Response.status(statusCode).entity(entity).build();
	}

	public static String populate() throws JSONException {
		JSONObject responseObject = new JSONObject();
		try {
			fuelQuoteParameters parameters = fuelQuoteParametersDataManager.fetchLastEntry();
			
			if (parameters == null) {
				responseObject.put("Status", HttpStatus.SC_UNAUTHORIZED);
				responseObject.put("message", "Error occured while fetching the data.");
				return responseObject.toString();
			} else {
				
				responseObject.put("baserate", parameters.getBaseRate());
				responseObject.put("profitmargin", parameters.getProfitMargin());
				responseObject.put("fluctuationsummer", parameters.getFluctuationSummer());
				responseObject.put("fluctuationwinter", parameters.getFluctuationWinter());
				responseObject.put("trpm", parameters.getTransportationRatePerMile());
				responseObject.put("startdate", parameters.getStartDate());
			}
		} catch (AppException e) {
			e.printStackTrace();
			responseObject.put("Status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
			responseObject.put("message", "Error occured while fetching the parameters.");
		}
		//System.out.println(responseObject.toString());
		return responseObject.toString();
		//return "hello its message";
	}
}