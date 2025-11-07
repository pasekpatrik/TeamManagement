package cz.cvut.fel.teammanagement.exceptions;

public class NoEntityFoundException extends RuntimeException {
    public NoEntityFoundException(String message) {
        super(message);
    }
}
