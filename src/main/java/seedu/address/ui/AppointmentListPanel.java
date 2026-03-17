package seedu.address.ui;

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
 * Panel containing the list of weekly appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private static final Comparator<Person> APPOINTMENT_ORDER =
            Comparator.comparing((Person person) -> person.getAppointmentStart().orElse(LocalDateTime.MAX))
                    .thenComparing(person -> person.getName().fullName.toLowerCase());
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);
    private final SortedList<Person> appointmentList;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates an {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public AppointmentListPanel(ObservableList<Person> personList) {
        super(FXML);
        appointmentList = new SortedList<>(personList, APPOINTMENT_ORDER);
        personListView.setItems(appointmentList);
        personListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an appointment using an {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
