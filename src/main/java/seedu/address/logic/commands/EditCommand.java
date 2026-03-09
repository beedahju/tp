package seedu.address.logic.commands;

/**
 * Edits an entity in the address book via a subcommand.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an entity via a subcommand.\n"
            + "Format: " + COMMAND_WORD + " SUBCOMMAND PARAMETERS\n"
            + "Example: " + COMMAND_WORD + " person 1 n/John Doe";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
}
