package personal.GesundKlinik.shared.exception;

public class AppointmentAlreadyCancelledException extends RuntimeException {

    public AppointmentAlreadyCancelledException(String message) {
        super(message);
    }
}
