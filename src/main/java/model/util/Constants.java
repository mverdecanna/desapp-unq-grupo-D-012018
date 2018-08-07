package model.util;

/**
 * Created by mariano on 02/06/18.
 */
public class Constants {


    public final static String TABLE_CURRENT_ACCOUNT = "current_account";


    //  MAIL DE LA APP
    public final static String CARPND_MAIL = "carpnd.2018.grupo.d@gmail.com";
    public final static String CARPND_PASSWORD = "traemelacopamessi";


    //  SUBJECTS
    public final static String SUBJECT_CREATE_RENTAL_OWNER = "CARPND - Peticion de alquiler";
    public final static String SUBJECT_CREATE_RENTAL_CLIENT = "CARPND - Hiciste una reserva";
    public final static String SUBJECT_CONFIRM_RENTAL_OWNER = "CARPND - Confirmaste el alquiler de tu vehiculo";
    public final static String SUBJECT_CONFIRM_RENTAL_CLIENT = "CARPND - Confirmacion de alquiler";
    public final static String SUBJECT_IN_USE_RENTAL_OWNER = "CARPND - ";
    public final static String SUBJECT_IN_USE_RENTAL_CLIENT = "CARPND - ";
    public final static String SUBJECT_REJECTED_RENTAL_OWNER = "CARPND - Rechazaste una peticion";
    public final static String SUBJECT_REJECTED_RENTAL_CLIENT = "CARPND - Rechazaron tu solicitud";
    public final static String SUBJECT_PAID_RENTAL_CLIENT = "CARPND - Realizaste un pago";
    public final static String SUBJECT_PAID_RENTAL_OWNER = "CARPND - Recibiste un pago";
    public final static String SUBJECT_COLLECT_RENTAL_OWNER = "CARPND - Retiraron el vehiculo";
    public final static String SUBJECT_COLLECT_RENTAL_CLIENT = "CARPND - Has retirado el vehiculo";
    public final static String SUBJECT_RETURNED_RENTAL_OWNER = "CARPND - Devolvieron tu vehiculo";
    public final static String SUBJECT_RETURNED_RENTAL_CLIENT = "CARPND - Has devuelto el vehiculo";
    public final static String SUBJECT_SCORED_RENTAL_CREATOR = "CARPND - Puntuaste a un usuario";
    public final static String SUBJECT_SCORED_RENTAL_USER = "CARPND - Recibiste una puntuación";


    //  BODYS
    public final static String BODY_CREATE_RENTAL_OWNER = "Alguien quiere alquilar uno de tus vehiculos.  Ingresa a la app para CONFIRMAR.";
    public final static String BODY_CREATE_RENTAL_CLIENT = "Hiciste una reserva para alquilar un vehiculo.  Estamos a la espera de la CONFIRMACION.";
    public final static String BODY_CONFIRM_RENTAL_OWNER = "Confirmaste el alquiler!  Ingresa a la app para poder seguir el proceso.";
    public final static String BODY_CONFIRM_RENTAL_CLIENT = "Han CONFIRMADO tu alquiler.  Ingresa a la app para avanzar con el proceso.";
    public final static String BODY_IN_USE_RENTAL_OWNER = "";
    public final static String BODY_IN_USE_RENTAL_CLIENT = "";
    public final static String BODY_RETURNED_RENTAL_OWNER = "Han devuelto tú vehiculo.  Ingresa a la app para ver el proceso.";
    public final static String BODY_RETURNED_RENTAL_CLIENT = "Has devuelto el vehiculo.  Ojalá que lo hayas disfrutado, ingresa a la app y planifica tu próximo alquiler.";
    public final static String BODY_COLLECT_RENTAL_OWNER = "Han pasado a retirar tú vehiculo.  Ingresa a la app para ver el proceso.";
    public final static String BODY_COLLECT_RENTAL_CLIENT = "Has retirado el vehiculo.  Que lo disfrutes!";
    public final static String BODY_REJECTED_RENTAL_OWNER = "Rechazaste una solicitud de alquiler.  El cliente ha sido debidamente notificado.";
    public final static String BODY_REJECTED_RENTAL_CLIENT = "Han rechazado tu solicitud de alquiler.  Ingresa a la app para buscar un nuevo vehiculo.";
    public final static String BODY_PAID_RENTAL_OWNER_START = "Te pagaron por alquilar uno de tus vehiculos. Se acredito en tu cuenta: ";
    public final static String BODY_PAID_RENTAL_OWNER_END = " creditos.  Ingresa a la app para chequearlo.";
    public final static String BODY_PAID_RENTAL_CLIENT_START = "Hiciste un pago por el alquiler de un vehiculo.  Abonaste: ";
    public final static String BODY_PAID_RENTAL_CLIENT_END = " creditos.  Ingresa a la app para poder seguir el proceso.";
    public final static String BODY_SCORED_RENTAL_CREATOR = "CARPND - Has puntuado al usuario con: ";
    public final static String BODY_SCORED_RENTAL_USER = "CARPND - Te han puntuado con: ";

//WAIT_CONFIRM, CONFIRM, IN_USE, DONE, REJECTED, CANCEL, RETURNED, SCORED



    // EXCEPTIONS

    public final static String INSUFFICIENT_BALANCE_MESSAGE = "El saldo es insuficiente para realizar la operación";
    public final static String INVALID_EMAIL_MESSAGE = "El EMAIL ingresado es incorrecto";
    public final static String EMAIL_ALREADY_EXISTS_MESSAGE = "El EMAIL ingresado ya está registrado";
    public final static String CUIL_ALREADY_EXISTS_MESSAGE = "El CUIL ingresado ya está registrado";
    public final static String INVALID_CUIL_MESSAGE = "El CUIL ingresado es incorrecto";
    public final static String PATENT_ALREADY_EXISTS_MESSAGE = "La patente ingresada ya está registrada";
    public final static String VEHICLE_INAVALID_DELETED_MESSAGE = "El vehiculo se encuentra alquilado, NO puede eliminarse";
    public final static String VEHICLE_INAVALID_RENTAL_MESSAGE = "El vehiculo se encuentra alquilado en esa fecha!";
    public final static String RENTAL_REJECT_INAVALID_STATUS_MESSAGE = "La operación en curso ya no puede ser Cancelada";
    public final static String SCORED_INAVALID_STATUS_MESSAGE = "Todavía no se puede puntuar al usuario";
    public final static String BAD_REPUTATION_MESSAGE = "Tus puntuciones actuales NO te permiten rentar";
    public final static String COLLECT_OUT_OF_TERM_MESSAGE = "No podes retirar un vehiculo un día distinto al que te habías comprometido";
    public final static String RETURNED_OUT_OF_TERM_MESSAGE = "No podes devolver un vehiculo fuera de termino";

}
