package utility;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * The Class HibernateUtility.
 */
public class HibernateUtility {
    
    private HibernateUtility() {
        throw new IllegalStateException("HibernateUtility class");
    }

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        } catch (HibernateException hibernateException) {
        	System.out.println("Hibernate Exception");
            hibernateException.printStackTrace();
        } catch (RuntimeException runtimeException) {
        	System.out.println("RunTime Exception");
            runtimeException.printStackTrace();
        }
    }

    /**
     * Gets the session.
     * @return the session
     */
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Close session.
     * @param session the session
     */
    public static void closeSession(Session session) {
        if (session != null)
            session.close();
    }
}
