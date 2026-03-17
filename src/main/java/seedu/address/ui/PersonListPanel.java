package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private static final Comparator<Person> APPOINTMENT_ORDER =
            Comparator.comparing((Person person) -> person.getAppointmentStart().orElse(LocalDateTime.MAX))
                    .thenComparing(person -> person.getName().fullName.toLowerCase());
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final ObservableList<Person> personList;
    private final SortedList<Person> appointmentList;
    private DisplayMode displayMode = DisplayMode.PERSON;

    @FXML
    private ListView<Person> personListView;

    /**
     * Rendering mode for the shared list panel.
     */
    public enum DisplayMode {
        PERSON,
        APPOINTMENT
    }

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;
        appointmentList = new SortedList<>(personList, APPOINTMENT_ORDER);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Updates how each row in the list is rendered.
     */
    public void setDisplayMode(DisplayMode displayMode) {
        requireNonNull(displayMode);
        this.displayMode = displayMode;

        if (displayMode == DisplayMode.APPOINTMENT) {
            personListView.setItems(appointmentList);
        } else {
            personListView.setItems(personList);
        }
        personListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (displayMode == DisplayMode.APPOINTMENT) {
                    setGraphic(new AppointmentCard(person, getIndex() + 1).getRoot());
                } else {
                    setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
