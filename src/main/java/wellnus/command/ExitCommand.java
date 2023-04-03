package wellnus.command;

import java.util.HashMap;

import wellnus.exception.BadCommandException;
import wellnus.ui.TextUi;

/**
 * Provides the exit command of the WellNUS++ app.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_DESCRIPTION = "exit - Close WellNUS++ and return to your terminal.";
    public static final String COMMAND_USAGE = "usage: exit";
    public static final String COMMAND_KEYWORD = "exit";
    private static final String COMMAND_INVALID_COMMAND_MESSAGE = "Invalid command given, expected 'exit'!";
    private static final String FEATURE_KEYWORD = "";
    private final TextUi textUi;

    /**
     * Initialises an ExitCommand Object using the arguments issued by the user.
     *
     * @param arguments Command arguments issued by the user
     * @see ExitCommand#validateCommand(HashMap)
     */
    public ExitCommand(HashMap<String, String> arguments) {
        super(arguments);
        this.textUi = new TextUi();
    }

    public static boolean isExit(Command command) {
        return command instanceof ExitCommand;
    }

    private TextUi getTextUi() {
        return this.textUi;
    }

    /**
     * Identifies this Command's keyword. Override this in subclasses so
     * toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    @Override
    protected String getCommandKeyword() {
        return ExitCommand.COMMAND_KEYWORD;
    }

    /**
     * Identifies the feature that this Command is associated with. Override
     * this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return ExitCommand.FEATURE_KEYWORD;
    }

    /**
     * Exits the WellNUS++ application.
     */
    @Override
    public void execute() {
    }

    /**
     * Validate the arguments passed by the user
     *
     * @param arguments Argument-Payload map generated by CommandParser using the user's command
     * @throws BadCommandException If the commandMap has any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (!arguments.containsKey(ExitCommand.COMMAND_KEYWORD)) {
            throw new BadCommandException(ExitCommand.COMMAND_INVALID_COMMAND_MESSAGE);
        }
    }

    /**
     * Method to ensure that developers add in a command usage.
     * <p>
     * For example, for the 'add' command in AtomicHabit package: <br>
     * "usage: add --name (name of habit)"
     *
     * @return String of the proper usage of the habit
     */
    @Override
    public String getCommandUsage() {
        return COMMAND_USAGE;
    }

    /**
     * Method to ensure that developers add in a description for the command.
     * <p>
     * For example, for the 'add' command in AtomicHabit package: <br>
     * "add - add a habit to your list"
     *
     * @return String of the description of what the command does
     */
    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }
}
