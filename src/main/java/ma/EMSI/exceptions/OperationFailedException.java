package ma.EMSI.exceptions;

public class OperationFailedException extends Exception{
    private String message;
    public OperationFailedException(String message){
        if (message == null) this.message = "Exception: Operation Failed !";
        this.message = message;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
