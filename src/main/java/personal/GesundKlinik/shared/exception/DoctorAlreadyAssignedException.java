package personal.GesundKlinik.shared.exception;

public class DoctorAlreadyAssignedException extends RuntimeException {

    public DoctorAlreadyAssignedException(String message) {
        super(message);
    }
}
