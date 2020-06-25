package dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import entity.FuelQuoteClient;
import exception.AppException;
import utility.HibernateUtility;

public class FuelQuoteClientDataManager {

	public static int create(FuelQuoteClient fuelQuoteClient) throws AppException {
		Session dbSession = HibernateUtility.getSession();
		Transaction transaction = dbSession.beginTransaction();
		try {
			dbSession.saveOrUpdate(fuelQuoteClient);
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
		return fuelQuoteClient.getId();
	}

	public static List<FuelQuoteClient> fetchAll(int userId, Date startRange, Date endRange) {
		Session dbSession = HibernateUtility.getSession();
		Criteria criteria = dbSession.createCriteria(FuelQuoteClient.class);
		
		if (userId > 0) {
			criteria.add(Restrictions.eq("userId", userId));
		}

		if (startRange != null && endRange != null) {
			criteria.add(Restrictions.between("deliveryDate", startRange, endRange));
		}
		return (List<FuelQuoteClient>) criteria.list();
	}

	public static List<FuelQuoteClient> fetchById(int userId) {
		Session dbSession = HibernateUtility.getSession();
		Criteria criteria = dbSession.createCriteria(FuelQuoteClient.class);
		
		if(userId > 0 ) {
			criteria.add(Restrictions.eq("userId", userId));
		}
		
		//criteria.add(Restrictions.between("deliveryDate", startDate, endDate));
		
		return (List<FuelQuoteClient>) criteria.list();
	}
}
