package service;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import dao.AuthenticationDataManager;
import entity.UserCredential;
import exception.AppException;

public class AuthenticationService {

	public static Response create(String username, String password) {
		UserCredential userCredential = null;
		String entity;
		int statusCode;
		try {
			userCredential = AuthenticationDataManager.fetchByName(username);
			if(userCredential != null) {
				statusCode = HttpStatus.SC_OK;
				entity = "Username already Exists, Please try something else.";
				return Response.status(statusCode).entity(entity).build();
			}
			
			userCredential = new UserCredential();
			userCredential.setUsername(username);
			userCredential.setPassword(password);
			userCredential.setAdmin(false);
			userCredential.setProfileComplete(false);
			new AuthenticationDataManager().create(userCredential);
			
			statusCode = HttpStatus.SC_OK;
			entity = "Success";
		} catch (AppException e) {
			e.printStackTrace();
			statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			entity = "Error occured while creating the user. The Error is on our end, we'll try to resolve asap.";
		}
		return Response.status(statusCode).entity(entity).build();
	}

	public static Response authenticate(String username, String password) {
		String message = "";
		int statusCode;
		try {
			UserCredential userCredential = AuthenticationDataManager.fetchByName(username);
			if(userCredential == null) {
				statusCode = HttpStatus.SC_OK;
				message = "Incorrect username, please try again.";
			}else {
				
				if(userCredential.getPassword().equals(password)) {
					statusCode = HttpStatus.SC_OK;
					if(userCredential.getAdmin() == true) {
						message = "isAdmin = 1";
					}
					else if(userCredential.getProfileComplete() == true) {
						message = "ProfileComplete = 1";
					}
					else {
						message = "ProfileComplete = 0";
					}
					
				}else {
					statusCode = HttpStatus.SC_OK;
					message = "Incorrect Password, please try again.";
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
			statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			message = e.getLocalizedMessage();
		}
		
		return Response.status(statusCode).entity(message).build();
		
	}
	
}
