package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Guardian} fields match the given search criteria.
 * Matching is case-insensitive. All specified fields must match (AND logic).
 * Within a name search, any keyword matching is sufficient (OR logic).
 * Persons without a guardian never match.
 */
public class GuardianContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final String phoneKeyword;
    private final String emailKeyword;

    /**
     * Constructs a predicate with optional search criteria for each guardian field.
     *
     * @param nameKeywords List of name keywords to match (empty list means no name filter).
     * @param phoneKeyword Phone keyword to match, or {@code null} for no phone filter.
     * @param emailKeyword Email keyword to match, or {@code null} for no email filter.
     */
    public GuardianContainsKeywordsPredicate(List<String> nameKeywords,
                                             String phoneKeyword, String emailKeyword) {
        this.nameKeywords = nameKeywords;
        this.phoneKeyword = phoneKeyword;
        this.emailKeyword = emailKeyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getGuardian()
                .map(guardian -> matchesName(guardian)
                        && matchesPhone(guardian)
                        && matchesEmail(guardian))
                .orElse(false);
    }

    private boolean matchesName(Guardian guardian) {
        if (nameKeywords.isEmpty()) {
            return true;
        }
        return guardian.getName() != null
                && nameKeywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(guardian.getName().fullName, keyword));
    }

    private boolean matchesPhone(Guardian guardian) {
        if (phoneKeyword == null) {
            return true;
        }
        return guardian.getPhone()
                .map(phone -> StringUtil.containsWordIgnoreCase(phone.value, phoneKeyword))
                .orElse(false);
    }

    private boolean matchesEmail(Guardian guardian) {
        if (emailKeyword == null) {
            return true;
        }
        return guardian.getEmail()
                .map(email -> StringUtil.containsWordIgnoreCase(email.value, emailKeyword))
                .orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GuardianContainsKeywordsPredicate)) {
            return false;
        }

        GuardianContainsKeywordsPredicate o = (GuardianContainsKeywordsPredicate) other;
        return nameKeywords.equals(o.nameKeywords)
                && Objects.equals(phoneKeyword, o.phoneKeyword)
                && Objects.equals(emailKeyword, o.emailKeyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("phoneKeyword", phoneKeyword)
                .add("emailKeyword", emailKeyword)
                .toString();
    }
}
