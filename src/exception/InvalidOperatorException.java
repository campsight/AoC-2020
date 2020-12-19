package exception;

public class InvalidOperatorException extends RuntimeException {

    public InvalidOperatorException(int opcode, int pointer, String description) {
        super(opcode + " is not a valid opcode (position: " + pointer + "). " + description);
    }

    public InvalidOperatorException(String operation) {
        super(operation + " is not a valid operation");
    }
}
