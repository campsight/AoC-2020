import java.util.ArrayList;

public class Program {
    public static int RETURN_ERROR = -1;
    public static int RETURN_OK = 99;

    private ArrayList<Operation> operations;
    private long accumulator;
    private int instructionPointer;
    private ArrayList<Operation> operationsRun;

    public Program(ArrayList<Operation> operations) {
        this.operations = operations;
        this.accumulator = 0;
        this.instructionPointer = 0;
        operationsRun = new ArrayList<>();
    }

    private boolean runNextInstruction() {
        Operation op = operations.get(instructionPointer);
        if (operationsRun.contains(op)) {
            return true;
        }
        operationsRun.add(op);
        switch (op.getOperation()) {
            case Operation.OP_NOP -> instructionPointer += 1;
            case Operation.OP_ACC -> {
                accumulator += op.getArgument();
                instructionPointer += 1;
            }
            case Operation.OP_JMP -> instructionPointer += op.getArgument();
            default -> System.out.println("ERROR!! operation " + op + " ERROR!!");
        }
        return false;
    }

    public int runProgram() {
        boolean loopFound = false;
        boolean programEnd = false;
        while ((!loopFound) && (!programEnd)) {
            loopFound = runNextInstruction();
            //System.out.println("running instruction " + instructionPointer);
            programEnd = (instructionPointer >= operations.size());
        }
        if (loopFound) {
            return RETURN_ERROR;
        } else if (instructionPointer == operations.size()) {
            return RETURN_OK;
        } else {
            return RETURN_ERROR;
        }
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public long getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(long accumulator) {
        this.accumulator = accumulator;
    }

    public int getInstructionPointer() {
        return instructionPointer;
    }

    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
    }
}
