package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPaymentCommand;

public class AddPaymentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPaymentCommand.MESSAGE_USAGE);
    private static final String VALID_DATE = "2026-01-13";
    private static final String FUTURE_DATE = "2026-01-14";
    private static final String VALID_DATE_DESC = " " + PREFIX_DATE + VALID_DATE;

    private final Clock fixedClock =
            Clock.fixed(Instant.parse("2026-01-13T00:00:00Z"), ZoneId.of("Asia/Singapore"));
    private final AddPaymentCommandParser parser = new AddPaymentCommandParser(fixedClock);

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1" + VALID_DATE_DESC,
                new AddPaymentCommand(INDEX_FIRST_PERSON, LocalDate.parse(VALID_DATE)));
    }

    @Test
    public void parse_missingDate_failure() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_negativeIndex_failure() {
        assertParseFailure(parser, "-1" + VALID_DATE_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_zeroIndex_failure() {
        assertParseFailure(parser, "0" + VALID_DATE_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonNumericPreamble_failure() {
        assertParseFailure(parser, "1 abc" + VALID_DATE_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {
        assertParseFailure(parser, "1 " + PREFIX_DATE + "2026-13-40", ParserUtil.MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_dateAfterToday_failure() {
        assertParseFailure(parser, "1 " + PREFIX_DATE + FUTURE_DATE, ParserUtil.MESSAGE_INVALID_DATE_AFTER_TODAY);
    }

    @Test
    public void parse_duplicateDatePrefix_failure() {
        assertParseFailure(parser, "1 d/2026-01-13 d/2026-01-12",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }
}
