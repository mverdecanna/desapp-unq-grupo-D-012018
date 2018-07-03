package service;

import model.Vehicle;
import model.builder.VehicleBuilder;
import org.springframework.transaction.annotation.Transactional;
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



    public List<Vehicle> vehicleList(String userId){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        return vehicleRepository.userVehicles(userId);
    }


    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        Vehicle newVehicle = this.makeNewVehicle(vehicle);
        vehicleRepository.save(newVehicle);
        return newVehicle;
    }


    private Vehicle makeNewVehicle(Vehicle vehicle){
        Vehicle newVehicle = new VehicleBuilder().setVehicleType(vehicle.getType()).setCapacity(vehicle.getCapacity()).setLocation(vehicle.getLocation())
                .setRetirementAddress(vehicle.getRetirementAddress()).setReturnAddress(vehicle.getReturnAddress()).setDescription(vehicle.getDescription()).
                        setPhone(vehicle.getPhone()).setCost(vehicle.getCost()).setOwnerCuil(vehicle.getOwnerCuil()).setPhoto(vehicle.getPhoto()).build();
        return newVehicle;
    }



    public List<Vehicle> othersVehicles(String userId){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        return vehicleRepository.notUserVehicles(userId);
    }



}
