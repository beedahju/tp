package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.FindParentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.GuardianContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindParentCommand object.
 * Expected format: {@code [n/NAME] [p/PHONE] [e/EMAIL]}
 * At least one prefix must be provided.
 */
public class FindParentCommandParser implements Parser<FindParentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindParentCommand
     * and returns a FindParentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindParentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
        }

        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean hasEmail = argMultimap.getValue(PREFIX_EMAIL).isPresent();

        if (!hasName && !hasPhone && !hasEmail) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = Collections.emptyList();
        if (hasName) {
            String nameValue = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (nameValue.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
            }
            nameKeywords = Arrays.asList(nameValue.split("\\s+"));
        }

        String phoneKeyword = null;
        if (hasPhone) {
            phoneKeyword = argMultimap.getValue(PREFIX_PHONE).get().trim();
            if (phoneKeyword.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
            }
        }

        String emailKeyword = null;
        if (hasEmail) {
            emailKeyword = argMultimap.getValue(PREFIX_EMAIL).get().trim();
            if (emailKeyword.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
            }
        }

        return new FindParentCommand(
                new GuardianContainsKeywordsPredicate(nameKeywords, phoneKeyword, emailKeyword));
    }
}
