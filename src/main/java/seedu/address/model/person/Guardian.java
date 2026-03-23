package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Guardian (parent/caretaker) of a Person in the address book. A Guardian is a contact belonging to a
 * student, containing optional name, phone, and email fields. This is a value object — immutable once created.
 */
public class Guardian {

    private final Name name;
    private final Phone phone;
    private final Email email;

    /**
     * Constructs a {@code Guardian}. Any field may be {@code null}.
     */
    public Guardian(Name name, Phone phone, Email email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Guardian)) {
            return false;
        }

        Guardian o = (Guardian) other;
        return Objects.equals(name, o.name) && Objects.equals(phone, o.phone) && Objects.equals(email, o.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).add("phone", phone).add("email", email).toString();
    }
}
