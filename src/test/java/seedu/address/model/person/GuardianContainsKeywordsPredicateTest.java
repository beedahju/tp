package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GuardianContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        GuardianContainsKeywordsPredicate firstPredicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("first"), null, null);
        GuardianContainsKeywordsPredicate secondPredicate =
                new GuardianContainsKeywordsPredicate(Arrays.asList("first", "second"), null, null);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuardianContainsKeywordsPredicate firstPredicateCopy =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("first"), null, null);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different phone -> returns false
        GuardianContainsKeywordsPredicate phonePredicate =
                new GuardianContainsKeywordsPredicate(Collections.emptyList(), "91234567", null);
        assertFalse(firstPredicate.equals(phonePredicate));
    }

    @Test
    public void test_guardianNameContainsKeywords_returnsTrue() {
        // One keyword matching guardian name
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("Alice"), null, null);
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Tan").build()));

        // Multiple keywords, one matches guardian name
        predicate = new GuardianContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), null, null);
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Tan").build()));

        // Mixed-case keyword
        predicate = new GuardianContainsKeywordsPredicate(Collections.singletonList("aLiCe"), null, null);
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Tan").build()));
    }

    @Test
    public void test_guardianPhoneContainsKeywords_returnsTrue() {
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.emptyList(), "91234567", null);
        assertTrue(predicate.test(new PersonBuilder()
                .withParentName("Alice Tan")
                .withParentPhone("91234567")
                .build()));
    }

    @Test
    public void test_guardianEmailContainsKeywords_returnsTrue() {
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.emptyList(), null, "alice@example.com");
        assertTrue(predicate.test(new PersonBuilder()
                .withParentName("Alice Tan")
                .withParentEmail("alice@example.com")
                .build()));
    }

    @Test
    public void test_multipleFieldsAllMatch_returnsTrue() {
        // Name and phone both match
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("Alice"), "91234567", null);
        assertTrue(predicate.test(new PersonBuilder()
                .withParentName("Alice Tan").withParentPhone("91234567").build()));
    }

    @Test
    public void test_multipleFieldsOneFails_returnsFalse() {
        // Name matches but phone does not
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("Alice"), "99999999", null);
        assertFalse(predicate.test(new PersonBuilder()
                .withParentName("Alice Tan").withParentPhone("91234567").build()));
    }

    @Test
    public void test_noGuardian_returnsFalse() {
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("Alice"), null, null);
        // Person without guardian should not match
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void test_guardianDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(Collections.singletonList("Carol"), null, null);
        assertFalse(predicate.test(new PersonBuilder().withParentName("Alice Tan").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GuardianContainsKeywordsPredicate predicate =
                new GuardianContainsKeywordsPredicate(keywords, "91234567", "test@example.com");

        String expected = GuardianContainsKeywordsPredicate.class.getCanonicalName()
                + "{nameKeywords=" + keywords
                + ", phoneKeyword=91234567"
                + ", emailKeyword=test@example.com}";
        assertEquals(expected, predicate.toString());
    }
}
