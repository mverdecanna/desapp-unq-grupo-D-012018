package aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class Logging {

    final static Logger logger = Logger.getLogger(Logging.class);

    @Before("within(webservice.*Rest) && execution(public * *(..))")
    public void logServices(JoinPoint joinPoint) {

        totalLog(
                " service " + joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                MethodSignature.class.cast(joinPoint.getSignature()).getParameterNames(),
                joinPoint.getArgs()
        );

    }

    private void totalLog (String targetClassName, String signatureName, String[] signatureParameterNames, Object[] arguments  ) {

        logger.info("Method intercepted in " + targetClassName);
        logger.info("Starting logging...");
        logger.info("Entering in Method :  " + signatureName);

        for(String arg : signatureParameterNames) {
            if (arg.equals("userId")) {
                logger.info("User ID :  " + arg);
            }
        }

        for (int i=0 ; i<arguments.length ; i++){
            logger.info("  Argument : " + new Integer(i+1));
            logger.info("    Class : " + arguments[i].getClass());
            logger.info("    Value : " + arguments[i].toString());
        }

        logger.info("TRAEME LA COPA MESSI, TRAEME LA COPA  Terminating logging...");

    }
}
