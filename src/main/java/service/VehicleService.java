package service;

import model.Vehicle;
import persistence.VehicleRepository;

import java.util.List;

/**
 * Created by mariano on 13/05/18.
 */
public class VehicleService extends GenericService<Vehicle> {


    private static final long serialVersionUID = 1L;

/*
   private VehicleRepository vehicleRepository;


    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
*/

    public Integer nVehicles(){
        return super.count();
    }

/*
    public List<Vehicle> vehicleList(String userId){
        return this.getRepository().
    }
*/

}
