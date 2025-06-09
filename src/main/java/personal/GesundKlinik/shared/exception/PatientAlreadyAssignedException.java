package personal.GesundKlinik.shared.exception;

public class PatientAlreadyAssignedException extends RuntimeException {

    public PatientAlreadyAssignedException(String message) {
        super(message);
    }
}
