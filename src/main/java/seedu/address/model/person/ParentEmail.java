package seedu.address.model.person;

/**
 * Represents a Person's parent email address in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link Email#isValidEmail(String)}
 */
public class ParentEmail extends Email {

    /**
     * Constructs a {@code ParentEmail}.
     *
     * @param email A valid email address.
     */
    public ParentEmail(String email) {
        super(email);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ParentEmail)) {
            return false;
        }

        ParentEmail otherEmail = (ParentEmail) other;
        return value.equals(otherEmail.value);
    }

}
