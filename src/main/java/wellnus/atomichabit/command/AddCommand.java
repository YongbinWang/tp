package wellnus.atomichabit.command;

import java.util.ArrayList;
import java.util.HashMap;

import wellnus.atomichabit.feature.AtomicHabit;
import wellnus.atomichabit.feature.AtomicHabitList;
import wellnus.atomichabit.feature.AtomicHabitManager;
import wellnus.command.Command;
import wellnus.exception.AtomicHabitException;
import wellnus.exception.BadCommandException;
import wellnus.ui.TextUi;

/**
 * The AddCommand class is a command class that adds a new atomic habit to an AtomicHabitList.<br>
 */
public class AddCommand extends Command {
    public static final String COMMAND_DESCRIPTION = "add - Add a habit to your habit tracker.";
    public static final String COMMAND_USAGE = "usage: add --name (your habit name)";
    public static final String COMMAND_KEYWORD = "add";
    private static final String COMMAND_INVALID_ARGUMENTS_MESSAGE = "Invalid arguments given to 'add'!";
    private static final String DUPLICATE_HABIT_MESSAGE = "You already have this habit in your list!"
            + " Use 'update' instead.";
    private static final String COMMAND_NAME_ARGUMENT = "name";
    private static final String COMMAND_KEYWORD_ASSERTION = "The key should be add.";
    private static final String COMMAND_PAYLOAD_ASSERTION = "The payload should not be empty.";

    private static final int COMMAND_NUM_OF_ARGUMENTS = 2;
    private static final String COMMAND_WRONG_KEYWORD_MESSAGE = "Invalid command issued, expected 'add'!";
    private static final String FEEDBACK_STRING_ONE = "Yay! You have added a new habit:";
    private static final String FEEDBACK_STRING_TWO = "was successfully added";
    private static final String COMMAND_INVALID_COMMAND_NOTE = "Please try 'help' command to check the "
            + "available commands and their usages!";
    private final AtomicHabitList atomicHabits;
    private final TextUi textUi;

    /**
     * Constructs an AddCommand object.<br>
     *
     * @param arguments    Argument-Payload map generated by CommandParser.
     * @param atomicHabits The AtomicHabitList object to add the habit to.
     */
    public AddCommand(HashMap<String, String> arguments, AtomicHabitList atomicHabits) {
        super(arguments);
        this.atomicHabits = atomicHabits;
        this.textUi = new TextUi();
    }

    private AtomicHabitList getAtomicHabits() {
        return atomicHabits;
    }

    private TextUi getTextUi() {
        return textUi;
    }

    private boolean hasDuplicate(String newHabit, ArrayList<AtomicHabit> habitList) {
        for (AtomicHabit habit : habitList) {
            if (convertToBase(habit.getDescription()).equals(convertToBase(newHabit))) {
                return true;
            }
        }
        return false;
    }

    private String convertToBase(String habitName) {
        return habitName.toLowerCase().replaceAll("\\s", "");
    }

    /**
     * Identifies this Command's keyword. Override this in subclasses so
     * toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    @Override
    protected String getCommandKeyword() {
        assert COMMAND_KEYWORD != null : "COMMAND_KEYWORD cannot be null";
        return COMMAND_KEYWORD;
    }

    /**
     * Identifies the feature that this Command is associated with. Override
     * this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return AtomicHabitManager.FEATURE_NAME;
    }

    /**
     * Adds of the new atomic habit into our list of atomic habits.
     * <p>
     * After that, print a message telling the user what the new habit added is
     *
     * @throws AtomicHabitException If the habit already exists in the list
     */
    @Override
    public void execute() throws AtomicHabitException {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            this.getTextUi().printErrorFor(badCommandException, COMMAND_INVALID_COMMAND_NOTE);
            return;
        }
        assert super.getArguments().containsKey(COMMAND_KEYWORD) : COMMAND_KEYWORD_ASSERTION;
        String name = super.getArguments().get(AddCommand.COMMAND_NAME_ARGUMENT);
        if (hasDuplicate(name, atomicHabits.getAllHabits())) {
            throw new AtomicHabitException(DUPLICATE_HABIT_MESSAGE);
        }
        AtomicHabit habit = new AtomicHabit(name);
        this.getAtomicHabits().addAtomicHabit(habit);
        String messageToUser = FEEDBACK_STRING_ONE + System.lineSeparator();
        messageToUser += String.format("'%s' %s", habit, FEEDBACK_STRING_TWO);
        getTextUi().printOutputMessage(messageToUser);
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.<br>
     * <br>
     * If no exceptions are thrown, command is valid.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException If the arguments have any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (arguments.size() != AddCommand.COMMAND_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(AddCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (!arguments.containsKey(AddCommand.COMMAND_KEYWORD)) {
            throw new BadCommandException(AddCommand.COMMAND_WRONG_KEYWORD_MESSAGE);
        }
        if (arguments.get(COMMAND_KEYWORD) != "") {
            throw new BadCommandException(AddCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (!arguments.containsKey(AddCommand.COMMAND_NAME_ARGUMENT)) {
            throw new BadCommandException(AddCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
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


