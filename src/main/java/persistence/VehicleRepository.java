package persistence;

import model.Vehicle;

import java.util.List;

/**
 * Created by mariano on 13/05/18.
 */
public class VehicleRepository extends HibernateGenericDAO<Vehicle> implements GenericRepository<Vehicle> {

    //public static Logger log = Logger.getLogger(VehicleRepository.class);

    private static final long serialVersionUID = -40365L;


    @Override
    protected Class<Vehicle> getDomainClass() {
        return Vehicle.class;
    }

    @Override
    public Integer count(){
        return super.count();
    }

/*
    public List<Vehicle> userVehicles(String userId){
        List<Vehicle> vehicles = (List<Vehicle>) this.getHibernateTemplate().
                find("select v from " + this.persistentClass.getName() + " v"
                                + "join users_vehicles uv on v.id = uv.vehicle_id "
                                 + "where uv.cuil = " + userId);
        return vehicles;
    }
*/


    public List<Vehicle> userVehicles(String userId){
        List<Vehicle> vehicles = (List<Vehicle>) this.getHibernateTemplate().
                find("select v from " + this.persistentClass.getName() + " v"
                        + " where v.ownerCuil = " + userId);
        return vehicles;
    }


    //public void addRelationship()



}
