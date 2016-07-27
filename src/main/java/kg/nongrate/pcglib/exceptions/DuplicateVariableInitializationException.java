package kg.nongrate.pcglib.exceptions;

/**
 * This exception is called when Agent is builded with missing arguments or parameters
 */
public class DuplicateVariableInitializationException extends RuntimeException {
    public DuplicateVariableInitializationException() {
        super("One or multiple arguments are rewritten after first initialized in Agent");
    }

    public DuplicateVariableInitializationException(String methodName) {
        super("Method " + methodName + " was already called");
    }

    public DuplicateVariableInitializationException(String methodName, Throwable cause) {
        super("Method " + methodName + " was already called", cause);
    }

    public DuplicateVariableInitializationException(Throwable cause) {
        super(cause);
    }
}
