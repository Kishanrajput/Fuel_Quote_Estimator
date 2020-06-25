package dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.fuelQuoteParameters;
import exception.AppException;
import utility.HibernateUtility;

public class fuelQuoteParametersDataManager{
	
	public static int update(fuelQuoteParameters fuelquoteparams) throws AppException {
		
		Session dbSession = HibernateUtility.getSession();
		Transaction transaction = dbSession.beginTransaction();
		
		try {
			dbSession.saveOrUpdate(fuelquoteparams);
			transaction.commit();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new AppException("Error occured while updating the parameters.", hibernateException);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			throw new AppException("Error occured while updating the parameters. ", runtimeException);
		} finally {
			HibernateUtility.closeSession(dbSession);
		}
		return fuelquoteparams.getFqpid();	
	}
	
	
	public static fuelQuoteParameters fetchLastEntry() throws AppException {
		
		Session dbSession = HibernateUtility.getSession();

		try {
			Query query = dbSession.createQuery("from fuelQuoteParameters f order by f.fqpid DESC");
			query.setMaxResults(1);
			fuelQuoteParameters last = (fuelQuoteParameters) query.uniqueResult();
			return last;
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new AppException("Error occured while fetching the previous parameters.", hibernateException);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			throw new AppException("Error occured while fetching the previous parameters.", runtimeException);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new AppException("Error occured while fetching the previous parameters.", exception);
		} finally {
			HibernateUtility.closeSession(dbSession);	
		}
	}
}