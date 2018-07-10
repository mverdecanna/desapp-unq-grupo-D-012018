import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ArchitectureTest {



    // Se prueba que en el Generic Service ,todos los metodos son transaccionales.

    
    @Test
    public void testArchitectureGenericService() {
        boolean result = true;

        try {
            Method[] methodsVehicleService = Class.forName("service.VehicleService").getMethods();
            Method[] methodsRentalService = Class.forName("service.RentalService").getMethods();
            Method[] methodsUserService = Class.forName("service.GenericService").getMethods();

            Method[] superMethodsVehicle = Class.forName("service.VehicleService").getSuperclass().getMethods();
            Method[] superMethodsRental = Class.forName("service.RentalService").getSuperclass().getMethods();
            Method[] superMethodsUser = Class.forName("service.UserService").getSuperclass().getMethods();


            List<Method> allMethodsService = new ArrayList<>(Arrays.asList(methodsVehicleService));
            allMethodsService.addAll(new ArrayList<>(Arrays.asList(methodsRentalService)));
            allMethodsService.addAll(new ArrayList<>(Arrays.asList(methodsUserService)));


            List<Method> superClassMethods = new ArrayList<>(Arrays.asList(superMethodsVehicle));
            superClassMethods.addAll(new ArrayList<>(Arrays.asList(superMethodsRental)));
            superClassMethods.addAll(new ArrayList<>(Arrays.asList(superMethodsUser)));

            for (Method method : allMethodsService) {
                if ( !(method.getName().contains("Service") || superClassMethods.contains(method)) ) {
                    Annotation[] annotations = method.getAnnotations();
                    if (annotations.length == 0) {
                        result = false;
                    }
                    for (Annotation annotation : annotations) {
                        result &= annotation.toString().startsWith("@org.springframework.transaction.annotation.Transactional");
                    }
                }
            }

            Assert.assertTrue(result);

        } catch (SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
