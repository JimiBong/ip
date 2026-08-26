package penny;

/**
 * Enum for all the commands penny supports.
 */
public enum Command {
    BYE,
    LIST,
    DUE,
    FIND,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;

    /**
     * Returns the Command parsed from commandString.
     *
     * @param commandString the string to parse for a command.
     * @throws PennyException if the command is blank.
     */
    public static Command parse(String commandString) throws PennyException {
        if (commandString.isBlank()) {
            throw new PennyException("Sorry I didn't quite catch that.");
        }

        try {
            return Command.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}