import exception.InvalidOperatorException;

public class Operation {
    public static final String OP_ACC = "acc";
    public static final String OP_JMP = "jmp";
    public static final String OP_NOP = "nop";

    private String operation;
    private int argument;

    public Operation(String operation, int argument) throws InvalidOperatorException {
        this.operation = operation;
        this.argument = argument;
        if (!(OP_ACC.equalsIgnoreCase(operation) || OP_JMP.equalsIgnoreCase(operation) || OP_NOP.equalsIgnoreCase(operation))) {
            throw new InvalidOperatorException(0,0, "Invalid operation: " + operation);
        }
    }

    public void swapOperation() {
        if (OP_NOP.equalsIgnoreCase(operation)) {
            setOperation(OP_JMP);
        } else if (OP_JMP.equalsIgnoreCase(operation)) {
            setOperation(OP_NOP);
        }
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        if (!(OP_ACC.equalsIgnoreCase(operation) || OP_JMP.equalsIgnoreCase(operation) || OP_NOP.equalsIgnoreCase(operation))) {
            throw new InvalidOperatorException(0,0, "Invalid operation: " + operation);
        }
        this.operation = operation;
    }

    public int getArgument() {
        return argument;
    }

    public void setArgument(int argument) {
        this.argument = argument;
    }
}
