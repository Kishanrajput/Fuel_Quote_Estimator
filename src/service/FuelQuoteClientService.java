package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.AuthenticationDataManager;
import dao.FuelQuoteClientDataManager;
import dao.UserProfileDataManager;
import dao.fuelQuoteParametersDataManager;
import entity.FuelQuoteClient;
import entity.User;
import entity.UserCredential;
import entity.fuelQuoteParameters;
import exception.AppException;

public class FuelQuoteClientService {

	public static String create(int numberOfGallons, String deliveryDate, double perGallon, double discount,
			double transportation, double finalprice, double totalAmount, String username) throws JSONException {

		FuelQuoteClient fuelQuoteClient;
		String entity;
		int statusCode;
		JSONObject response = new JSONObject();
		if (numberOfGallons == 0 || deliveryDate.length() == 0 || perGallon == 0 || totalAmount == 0
				|| username.length() == 0) {
			response.put("status", HttpStatus.SC_OK);
			response.put("message", "Incomplete data");
			return response.toString();
			//return Response.status(HttpStatus.SC_OK).entity("Incomplete data").build().toString();
		}

		try {
			fuelQuoteClient = new FuelQuoteClient();
			UserCredential user = AuthenticationDataManager.fetchByUsername(username);
			
			fuelQuoteClient.setNumberOfGallons(numberOfGallons);
			fuelQuoteClient.setDeliveryDate(convertStringToDate(deliveryDate));
			fuelQuoteClient.setPerGallon(perGallon);
			fuelQuoteClient.setDiscount(discount);
			fuelQuoteClient.setTransportationRate(transportation);
			fuelQuoteClient.setPriceAfterDiscount(finalprice);
			fuelQuoteClient.setTotalAmount(totalAmount);
			fuelQuoteClient.setUserId(user.getId());

			FuelQuoteClientDataManager.create(fuelQuoteClient);

			entity = "Your Order is placed successfully. Fuel will be delivered in 3 business days.";
			//System.out.println(entity);
			statusCode = HttpStatus.SC_OK;
			response.put("status", statusCode);
			response.put("message", entity);
			return response.toString();
		} catch (AppException appException) {
			appException.printStackTrace();
			entity = "Error occured while  saving the fuel quote for client : " + username;
			statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			response.put("status", statusCode);
			response.put("message", entity);
		}

		//return Response.status(statusCode).entity(entity).build().toString();
		return response.toString();
	}

	
	public static Date convertStringToDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String convertDateToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        return dateFormat.format(date); 
	}
	
	public static List<FuelQuoteClient> fetchAll(String username, String startDate, String endDate) {
		try {
			Date startRange = null, endRange = null;
			System.out.println(username);
			int userId = 0;
			if (startDate != null && startDate.length() > 0)
				startRange = convertStringToDate(startDate);

			if (endDate != null && endDate.length() > 0)
				endRange = convertStringToDate(endDate);
			
			if (username != null && username.length() > 0 && !username.equals("undefined") ) {
				UserCredential userCredential = AuthenticationDataManager.fetchByUsername(username);
				if(userCredential == null) {
					return (new ArrayList<>());
				}
				userId = userCredential.getId();
			}
			List<FuelQuoteClient> fuelQuoteHistory = new ArrayList<FuelQuoteClient>();
			fuelQuoteHistory = FuelQuoteClientDataManager.fetchAll(userId, startRange, endRange);
			
			fuelQuoteHistory.stream().forEach(ele -> ele.setDeliveryDateStr(convertDateToString(ele.getDeliveryDate())));
			fuelQuoteHistory.forEach(ele -> {
				try {
					User user = UserProfileDataManager.fetchProfile(ele.getUserId());
					ele.setFirstName(user.getFirstName());
					ele.setAddress(user.getAddress1()+", "+user.getCity()+", "+user.getState()+" "+user.getZip());
				} catch (AppException e) {
					e.printStackTrace();
				}
			});
			
			return fuelQuoteHistory;
		} catch (AppException appException) {
			appException.printStackTrace();
			return (new ArrayList<>());
		}
	}
	
	
	public static String calculate(String username, String numOfGallons, String date) throws JSONException {
		
		JSONObject response = new JSONObject();
		int httpStatus;
		String entity;
		double basePrice = 0;
		//double transportationrate = 0;
		double locFactor=0;
		double rateHisFactor=0;
		double galFactor=0;
		double comProfitFactor = 0;
		double rateFlucFactor=0;
		double PricePerGallon=0;
		
		int userId = 0;
		
		if (username.length() == 0 ||Integer.parseInt(numOfGallons) == 0 || date.length() == 0 || username == null
				|| numOfGallons == null || date == null) {
			
			entity = "Incomplete data";
			response.put("Error", entity);
			return response.toString();
		}

		try {
			List<FuelQuoteClient> fuelQuoteHis = new ArrayList<FuelQuoteClient>();
			if(username != "None")
			{
				UserCredential user = AuthenticationDataManager.fetchByUsername(username);
				userId = user.getId();
			}
			System.out.println("Userid  "+userId);
			//Margin =  Current Price * (Location Factor - Rate History Factor + Gallons Requested Factor + Company Profit Factor + Rate Fluctuation)
			User user = UserProfileDataManager.fetchProfile(userId);
			fuelQuoteHis = FuelQuoteClientDataManager.fetchById(userId);
			
			fuelQuoteParameters param = fuelQuoteParametersDataManager.fetchLastEntry();
			
			basePrice = param.getBaseRate();
			
			//Calculation of transportation rate
			if(user.getState().equals("TX")) {
				locFactor=2.0;			//percentage value per mile per gallon on base price
			}
			else {
				locFactor=param.getTransportationRatePerMile();
			}
			
			double transportationPerMilePerGallon = basePrice * locFactor / 100;
			
			
			//Calculation of discount based on rate history
			if(fuelQuoteHis.size()==0) {
				
				rateHisFactor=0.0;
			}
			else {
				
				rateHisFactor=1.0;
			}
			
			double discount = basePrice * rateHisFactor / 100;		//discount per gallon 
			
			
			//Other charges calculation
			if(Integer.parseInt(numOfGallons) < 1000) {
				
				galFactor=0.02;
			}
			else {
				 
				galFactor=0.03;
			}
			
			comProfitFactor=param.getProfitMargin();
			
			String[] month=date.split("-");
			int m=Integer.parseInt(month[1]);
			//System.out.println("m  "+m);
			if(4<=m && m<=8) {
				rateFlucFactor=param.getFluctuationSummer();
			}
			else {
				rateFlucFactor=param.getFluctuationWinter();
			}
			
			double othercharges = basePrice * (galFactor + comProfitFactor + rateFlucFactor) / 100;
			
			
			PricePerGallon= basePrice+ transportationPerMilePerGallon + othercharges - discount;
			System.out.println(PricePerGallon);
			
			PricePerGallon = Math.round(PricePerGallon*1000.0)/1000.0;	//round upto 3 decimal places
			System.out.println(PricePerGallon);
			//System.out.println("sugPrice  "+sugPrice);
			
			
			response.put("perGallon", basePrice);
			response.put("transportationCharge", transportationPerMilePerGallon);
			response.put("discount", discount);
			response.put("finalPrice", PricePerGallon);
			float totalamount = (float) (PricePerGallon * Integer.parseInt(numOfGallons));
			response.put("totalamount", totalamount);
			response.put("Error", "No Error");
			//System.out.println(response.toString());
			return response.toString();
		} catch (AppException appException) {
			appException.printStackTrace();
			entity = "Error occured while creating the JSON Object ";
			return response.put("Error", entity).toString();
		}
		  
		
	}

}
