package persistence;


import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;

/**
 * Created by mariano on 14/04/18.
 */
public abstract class HibernateGenericDAO<T>  extends HibernateDaoSupport implements GenericRepository<T>, Serializable {



}
