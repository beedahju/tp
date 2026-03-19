package seedu.address.model.person;

/**
 * Represents a Person's parent phone number in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link Phone#isValidPhone(String)}
 */
public class ParentPhone extends Phone {

    /**
     * Constructs a {@code ParentPhone}.
     *
     * @param phone A valid phone number.
     */
    public ParentPhone(String phone) {
        super(phone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ParentPhone)) {
            return false;
        }

        ParentPhone otherPhone = (ParentPhone) other;
        return value.equals(otherPhone.value);
    }

}
