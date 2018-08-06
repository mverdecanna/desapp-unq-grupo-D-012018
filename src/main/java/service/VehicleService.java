package service;

import model.Vehicle;
import model.builder.VehicleBuilder;
import model.exceptions.InvalidRegisterParameterException;
import model.exceptions.VehicleAssociatedToActiveRentalException;
import org.springframework.transaction.annotation.Transactional;
import persistence.VehicleRepository;

import java.util.List;

import static model.util.Constants.PATENT_ALREADY_EXISTS_MESSAGE;
import static model.util.Constants.VEHICLE_INAVALID_DELETED_MESSAGE;

/**
 * Created by mariano on 13/05/18.
 */
public class VehicleService extends GenericService<Vehicle> {


    private static final long serialVersionUID = 1L;



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
        if(vehicle.getId().isEmpty() || this.registeredPatent(vehicle.getId())){
            throw new InvalidRegisterParameterException(PATENT_ALREADY_EXISTS_MESSAGE);
        }
    }


    private Boolean registeredPatent(String patent){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        Integer find = vehicleRepository.existPatent(patent);
        return find > 0;
    }



    @Transactional
    public void updateVehicle(Vehicle vehicle) throws InvalidRegisterParameterException {
        //this.validateNewVehicle(vehicle);
        this.update(vehicle);
    }



    @Transactional
    public void deleteVehicle(Vehicle vehicle) throws VehicleAssociatedToActiveRentalException {
        this.validateDeleteVehicle(vehicle);
        this.delete(vehicle);
    }


    private void validateDeleteVehicle(Vehicle vehicle) throws VehicleAssociatedToActiveRentalException {
        if(this.existActiveRentalAssociated(vehicle.getId())){
            throw new VehicleAssociatedToActiveRentalException(VEHICLE_INAVALID_DELETED_MESSAGE);
        }
    }


    @Transactional
    public Boolean existActiveRentalAssociated(String cuil){
        VehicleRepository vehicleRepository = (VehicleRepository) getRepository();
        Integer find = vehicleRepository.existInActiveRental(cuil);
        return find > 0;
    }



}
