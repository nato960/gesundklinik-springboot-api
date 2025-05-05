package personal.GesundKlinik.shared.exception;

public class EmailInUseException extends RuntimeException{

    public EmailInUseException(String message) {
        super(message);
    }

}
