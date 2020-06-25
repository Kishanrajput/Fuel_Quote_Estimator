package dao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import entity.User;
import entity.UserCredential;
import exception.AppException;
import utility.HibernateUtility;

public class UserProfileDataManager {
	public static int create(User userProfile) throws AppException {

		Session dbSession = HibernateUtility.getSession();
		Transaction transaction = dbSession.beginTransaction();
		
		try {
			dbSession.saveOrUpdate(userProfile);
			
			Query query = dbSession.createQuery("update UserCredential uc set uc.profileComplete = 1 where uc.id = "+userProfile.getId());
			int result = query.executeUpdate();
			System.out.println(result);
			transaction.commit();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new AppException("Error occured while updating profile of : "+userProfile.getFirstName(), hibernateException);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			throw new AppException("Error occured while updating profile of : "+userProfile.getFirstName(), runtimeException);
		} finally {
			HibernateUtility.closeSession(dbSession);
		}				
		return userProfile.getId();
	}
	
	public static User fetchProfile(int id) throws AppException {

		Session dbSession = HibernateUtility.getSession();
		//Transaction transaction = dbSession.beginTransaction();	
		
		try {
			Criteria userCriteria = dbSession.createCriteria(User.class);
			userCriteria.add(Restrictions.eq("id", id));
			return (User) userCriteria.uniqueResult();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new AppException("Error occured while fetching the Profile,", hibernateException);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			throw new AppException("Error occured while fetching the Profile,", runtimeException);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new AppException("Error occured while fetching the Profile,", exception);
		} finally {
			HibernateUtility.closeSession(dbSession);	
		}
	}
}
