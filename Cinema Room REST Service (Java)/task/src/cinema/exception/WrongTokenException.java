package cinema.exception;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException(String massage) {
        super(massage);
    }
}
