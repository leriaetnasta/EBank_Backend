package ma.EMSI.exceptions;

public class CustomerNotFoundException extends Exception{
    private String message;
    public CustomerNotFoundException(String message){
        if (message == null) this.message = "Customer Not Found";
        this.message = message;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
