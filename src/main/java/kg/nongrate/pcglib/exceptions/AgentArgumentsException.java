package kg.nongrate.pcglib.exceptions;

/**
 * This exception is called when Agent is builded with missing arguments or parameters
 */
public class AgentArgumentsException extends RuntimeException {
    public AgentArgumentsException() {
        super("One or multiple arguments are missing in Agent description");
    }

    public AgentArgumentsException(String message) {
        super("These methods also must be called, because something use them:\n" + message);
    }

    public AgentArgumentsException(String message, Throwable cause) {
        super("These methods also must be called, because something use them:\n" + message, cause);
    }

    public AgentArgumentsException(Throwable cause) {
        super(cause);
    }
}
