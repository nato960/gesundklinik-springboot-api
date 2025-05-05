package personal.GesundKlinik.shared.exception;

public class NoDoctorAvailableOnThisDateException extends RuntimeException {

    public NoDoctorAvailableOnThisDateException(String message) {
        super(message);
    }

}
