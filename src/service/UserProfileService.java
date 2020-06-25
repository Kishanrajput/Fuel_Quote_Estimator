package service;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import dao.UserProfileDataManager;
import entity.User;
import entity.UserCredential;
import exception.AppException;
import dao.AuthenticationDataManager;

public class UserProfileService {

	public static Response create(String username, String firstname, String lastname, String emailId, String phoneNumber, String address1, String address2, String state, String city, int zip)throws JSONException {
		JSONObject response = new JSONObject();
		String entity;
		int statusCode;
		try {
					
			
			if(firstname==null || firstname.length()==0|| lastname == null || emailId ==null || phoneNumber == null || address1 == null || state == null || city == null || zip == 0) {
				
				statusCode = HttpStatus.SC_OK ;
				entity = "There is some error in your data, Please fill the details carefully.";
				return Response.status(statusCode).entity(entity).build();	
			}
			else { 
				UserCredential user = AuthenticationDataManager.fetchByUsername(username);
				int id = user.getId();
				//System.out.println(id);
				User userProfile = new User();
				userProfile.setId(id);
				userProfile.setFirstName(firstname);
				userProfile.setLastName(lastname);
				userProfile.setEmailId(emailId);
				userProfile.setPhoneNumber(phoneNumber);
				userProfile.setAddress1(address1);
				userProfile.setAddress2(address2);
				userProfile.setState(state);
				userProfile.setCity(city);
				userProfile.setZip(zip);
				UserProfileDataManager.create(userProfile);
			
				statusCode = HttpStatus.SC_OK;
				entity = "Profile Updated successfully!";
			}
		} catch (AppException e) {
			e.printStackTrace();
			statusCode = HttpStatus.SC_OK;
			entity = "Error occured while creating the user";
		}
		return Response.status(statusCode).entity(entity).build();
	}
	
	
	public static String populate(String username)throws JSONException {
		
		JSONObject response = new JSONObject();
		String entity;
		int statusCode;	
		try {
					
			if(username == null || username.equals("")) {
				statusCode = HttpStatus.SC_OK ;
				entity = "Access Denied";
				response.put("Error", entity);
				
				return response.toString();	
			}
			else { 
				UserCredential user = AuthenticationDataManager.fetchByUsername(username);
				int id = user.getId();
				if(user.getProfileComplete() == false)
				{
					return response.put("Error", "NoProfile").toString();
				}
				User userProfile = UserProfileDataManager.fetchProfile(id);
				response.put("Error", "No Error");
				response.put("firstname", userProfile.getFirstName());
				response.put("lastname", userProfile.getLastName());
				response.put("emailId", userProfile.getEmailId());
				response.put("phone", userProfile.getPhoneNumber());
				response.put("add1", userProfile.getAddress1());
				response.put("add2", userProfile.getAddress2());
				response.put("city", userProfile.getCity());
				response.put("state", userProfile.getState());
				response.put("zip", userProfile.getZip());
				return response.toString();
			}
		} catch (AppException e) {
			e.printStackTrace();
			statusCode = HttpStatus.SC_OK;
			entity = "Error occured while creating the user";
			return response.put("Error", entity).toString();
		}
	}

}
