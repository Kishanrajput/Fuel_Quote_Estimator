package dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import entity.UserCredential;
import exception.AppException;
import utility.HibernateUtility;

public class AuthenticationDataManager { 

	public int create(UserCredential userCredential) throws AppException {

		Session dbSession = HibernateUtility.getSession();
		Transaction transaction = dbSession.beginTransaction();
		try {
			dbSession.saveOrUpdate(userCredential);
			transaction.commit();
			return userCredential.getId();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new AppException("Error occured while creating a user with username : "+userCredential.getUsername(), hibernateException);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			throw new AppException("Error occured while creating a user with username : "+userCredential.getUsername(), runtimeException);
		} finally {
			HibernateUtility.closeSession(dbSession);
		}
	}

	public static UserCredential fetchByName(String username) throws AppException {
		Session dbSession = HibernateUtility.getSession();

		try {
			Criteria userCriteria = dbSession.createCriteria(UserCredential.class);
			userCriteria.add(Restrictions.eq("username", username));
			return (UserCredential) userCriteria.uniqueResult();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new AppException("Error occured while fetching the username : "+username, hibernateException);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			throw new AppException("Error occured while fetching the username : "+username, runtimeException);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new AppException("Error occured while fetching the username : "+username, exception);
		} finally {
			HibernateUtility.closeSession(dbSession);	
		}
	}

	public static UserCredential fetchByUsername(String username) throws AppException{
		Session dbSession = HibernateUtility.getSession();

		try {
			Criteria usernameCriteria = dbSession.createCriteria(UserCredential.class);
			usernameCriteria.add(Restrictions.eq("username", username));
			return (UserCredential) usernameCriteria.uniqueResult();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new AppException("Error occured while fetching a user with username : " + username,
					hibernateException);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			throw new AppException("Error occured while fetching a user with username : " + username,
					runtimeException);
		} finally {
			HibernateUtility.closeSession(dbSession);
		}
	}
	
}
