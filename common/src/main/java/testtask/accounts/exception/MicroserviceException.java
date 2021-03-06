package testtask.accounts.exception;

/**
 *
 * @author Olga Grazhdanova <dvl.java@gmail.com> at Jan 27, 2018
 */
public class MicroserviceException extends RuntimeException {

    private final ErrorTypes type;
    private final String info;

    public enum ErrorTypes {
        business,
        validation,
        null_argument,
        not_found,
        bad_mks_request,
        mks_response_null,
        mks_response_unknown,
        db_error,
        other
    }

    public MicroserviceException(RuntimeException ex) {
        info = ex.getLocalizedMessage();
        type = ErrorTypes.other;
    }

    public MicroserviceException(ErrorTypes type, String info) {
        super(info);
        this.type = type;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public ErrorTypes getType() {
        return type;
    }

}
