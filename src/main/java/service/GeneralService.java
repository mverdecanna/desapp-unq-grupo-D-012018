package service;

/**
 * Created by mariano on 14/04/18.
 */
public class GeneralService {



    //  UserService
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(final UserService userService) {
        this.userService = userService;
    }
    //  UserService


    //  VehicleService
    private VehicleService vehicleService;

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    //  VehicleService


    //  RentalService
    private RentalService rentalService;

    public RentalService getRentalService() {
        return rentalService;
    }

    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

//  RentalService


}
