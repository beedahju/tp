package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindParentCommand;
import seedu.address.model.person.GuardianContainsKeywordsPredicate;

public class FindParentCommandParserTest {
    private FindParentCommandParser parser = new FindParentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixValue_throwsParseException() {
        assertParseFailure(parser, "n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nameOnly_returnsFindParentCommand() {
        FindParentCommand expected = new FindParentCommand(
                new GuardianContainsKeywordsPredicate(Arrays.asList("Alice", "Tan"), null, null));
        assertParseSuccess(parser, "n/Alice Tan", expected);
    }

    @Test
    public void parse_phoneOnly_returnsFindParentCommand() {
        FindParentCommand expected = new FindParentCommand(
                new GuardianContainsKeywordsPredicate(Collections.emptyList(), "91234567", null));
        assertParseSuccess(parser, "p/91234567", expected);
    }

    @Test
    public void parse_emailOnly_returnsFindParentCommand() {
        FindParentCommand expected = new FindParentCommand(
                new GuardianContainsKeywordsPredicate(
                        Collections.emptyList(), null, "alice@example.com"));
        assertParseSuccess(parser, "e/alice@example.com", expected);
    }

    @Test
    public void parse_allPrefixes_returnsFindParentCommand() {
        FindParentCommand expected = new FindParentCommand(
                new GuardianContainsKeywordsPredicate(
                        Collections.singletonList("Alice"), "91234567", "alice@example.com"));
        assertParseSuccess(parser, "n/Alice p/91234567 e/alice@example.com", expected);
    }
}
