package persistence;

import model.Vehicle;
import org.apache.log4j.Logger;

/**
 * Created by mariano on 13/05/18.
 */
public class VehicleRepository extends HibernateGenericDAO<Vehicle> implements GenericRepository<Vehicle> {


    public static Logger log = Logger.getLogger(VehicleRepository.class);

    private static final long serialVersionUID = -40365L;


    @Override
    protected Class<Vehicle> getDomainClass() {
        return Vehicle.class;
    }

    @Override
    public Integer count(){
        return super.count();
    }



}
