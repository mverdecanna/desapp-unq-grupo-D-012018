package service;

import model.Vehicle;
import model.builder.VehicleBuilder;
import model.exceptions.InvalidRegisterParameterException;
import org.springframework.transaction.annotation.Transactional;
import persistence.VehicleRepository;

import java.util.List;

import static model.util.Constants.PATENT_ALREADY_EXISTS_MESSAGE;

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

    private Integer nVehicles(){
        return super.count();
    }


    @Transactional
    public List<Vehicle> vehicleList(String userId){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        return vehicleRepository.userVehicles(userId);
    }


    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) throws InvalidRegisterParameterException {
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        this.validateNewVehicle(vehicle);
        //Vehicle newVehicle = this.makeNewVehicle(vehicle);
        vehicleRepository.save(vehicle);
        return vehicle;
    }


    private Vehicle makeNewVehicle(Vehicle vehicle){
        Vehicle newVehicle = new VehicleBuilder().setId(vehicle.getId()).setVehicleType(vehicle.getType()).setCapacity(vehicle.getCapacity()).setLocation(vehicle.getLocation())
                .setRetirementAddress(vehicle.getRetirementAddress()).setReturnAddress(vehicle.getReturnAddress()).setDescription(vehicle.getDescription()).
                        setPhone(vehicle.getPhone()).setCost(vehicle.getCost()).setOwnerCuil(vehicle.getOwnerCuil()).setPhoto(vehicle.getPhoto()).build();
        return newVehicle;
    }


    @Transactional
    public List<Vehicle> othersVehicles(String userId){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        return vehicleRepository.notUserVehicles(userId);
    }



    private void validateNewVehicle(Vehicle vehicle) throws InvalidRegisterParameterException {
        if(this.registeredPatent(vehicle.getId())){
            throw new InvalidRegisterParameterException(PATENT_ALREADY_EXISTS_MESSAGE);
        }
    }


    @Transactional
    public Boolean registeredPatent(String patent){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        Integer find = vehicleRepository.existPatent(patent);
        return find > 0;
    }




}
