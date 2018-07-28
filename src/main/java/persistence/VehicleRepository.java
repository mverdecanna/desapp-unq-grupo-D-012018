package persistence;

import model.Vehicle;
import org.springframework.dao.support.DataAccessUtils;

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



    public List<Vehicle> notUserVehicles(String userId){
        List<Vehicle> vehicles = (List<Vehicle>) this.getHibernateTemplate().
                find("select v from " + this.persistentClass.getName() + " v"
                        + " where v.ownerCuil <> " + userId);
        return vehicles;
    }


    public Integer existPatent(String id){
        String query = "SELECT count(*) FROM Vehicle as v WHERE v.id = :id";
        Integer count = DataAccessUtils.intResult(this.getHibernateTemplate().findByNamedParam(query, "id", id));
        return count;
    }





}
