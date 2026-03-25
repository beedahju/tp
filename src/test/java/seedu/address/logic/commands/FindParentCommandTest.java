package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GuardianContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindParentCommand}.
 */
public class FindParentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        GuardianContainsKeywordsPredicate firstPredicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("first"), null, null);
        GuardianContainsKeywordsPredicate secondPredicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("second"), null, null);

        FindParentCommand findFirstCommand = new FindParentCommand(firstPredicate);
        FindParentCommand findSecondCommand = new FindParentCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindParentCommand findFirstCommandCopy = new FindParentCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noMatch_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("NonExistent"), null, null);
        FindParentCommand command = new FindParentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("keyword"), "91234567", null);
        FindParentCommand findCommand = new FindParentCommand(predicate);
        String expected = FindParentCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}
