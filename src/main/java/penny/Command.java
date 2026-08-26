package penny;

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