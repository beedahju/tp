package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

public class JsonAdaptedAppointmentAttendanceTest {

    @Test
    public void toModelType_validDateTime_returnsAttendance() throws Exception {
        JsonAdaptedAppointmentAttendance adapted =
                new JsonAdaptedAppointmentAttendance(true, "2026-01-29T08:30:00");

        Attendance attendance = adapted.toModelType();

        assertEquals(new Attendance(true, LocalDateTime.of(2026, 1, 29, 8, 30)), attendance);
    }

    @Test
    public void toModelType_validDate_returnsAttendanceAtStartOfDay() throws Exception {
        JsonAdaptedAppointmentAttendance adapted =
                new JsonAdaptedAppointmentAttendance(false, "2026-01-29");

        Attendance attendance = adapted.toModelType();

        assertEquals(new Attendance(false, LocalDate.of(2026, 1, 29)), attendance);
    }

    @Test
    public void toModelType_nullRecordedDate_throwsIllegalValueException() {
        JsonAdaptedAppointmentAttendance adapted =
                new JsonAdaptedAppointmentAttendance(true, null);

        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "appointment attendance date");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidRecordedDate_throwsIllegalValueException() {
        JsonAdaptedAppointmentAttendance adapted =
                new JsonAdaptedAppointmentAttendance(true, "2026-13-40T25:00:00");

        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void constructor_roundTrip_returnsEquivalentAttendance() throws Exception {
        Attendance source = new Attendance(true, LocalDateTime.of(2026, 1, 29, 8, 30));
        JsonAdaptedAppointmentAttendance adapted = new JsonAdaptedAppointmentAttendance(source);

        assertEquals(source, adapted.toModelType());
    }
}
