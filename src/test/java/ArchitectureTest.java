
import org.junit.Test;
import org.junit.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import java.util.List;


public class ArchitectureTest {



    // Se prueba que en el Generic Service ,todos los metodos son transaccionales.

    
    @Test
    public void testArchiteture1() {
        boolean result = true;

        try {

            Method[] methods = Class.forName("service.GenericService").getMethods();
            Method[] superMethods = Class.forName("service.GenericService").getSuperclass().getMethods();

            List<Method> methodsClass = Arrays.asList(methods);
            List<Method> superClassMethods = Arrays.asList(superMethods);

            for (Method method : methodsClass) {
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
