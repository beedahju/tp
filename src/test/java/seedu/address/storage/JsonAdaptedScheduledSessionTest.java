package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.recurrence.Recurrence;
import seedu.address.model.session.ScheduledSession;

public class JsonAdaptedScheduledSessionTest {

    @Test
    public void toModelType_validSessionDetails_returnsScheduledSession() throws Exception {
        JsonAdaptedScheduledSession adaptedSession = new JsonAdaptedScheduledSession(
                "2026-01-13T08:00:00",
                "2026-01-20T08:00:00",
                "WEEKLY",
                " Algebra ",
                List.of(new JsonAdaptedAppointmentAttendance(true, "2026-01-13T08:00:00")));

        ScheduledSession session = adaptedSession.toModelType();

        assertEquals(Recurrence.WEEKLY, session.getRecurrence());
        assertEquals(LocalDateTime.of(2026, 1, 13, 8, 0), session.getStart());
        assertEquals(LocalDateTime.of(2026, 1, 20, 8, 0), session.getNext());
        assertEquals("Algebra", session.getDescription());
        assertEquals(1, session.getAttendanceHistory().getRecords().size());
        assertEquals(new Attendance(true, LocalDateTime.of(2026, 1, 13, 8, 0)),
                session.getAttendanceHistory().getRecords().get(0));
    }

    @Test
    public void toModelType_nullOptionalFields_usesDefaults() throws Exception {
        JsonAdaptedScheduledSession adaptedSession = new JsonAdaptedScheduledSession(
                "2026-01-13T08:00:00",
                null,
                null,
                null,
                null);

        ScheduledSession session = adaptedSession.toModelType();

        assertEquals(Recurrence.NONE, session.getRecurrence());
        assertEquals(LocalDateTime.of(2026, 1, 13, 8, 0), session.getStart());
        assertEquals(LocalDateTime.of(2026, 1, 13, 8, 0), session.getNext());
        assertEquals("", session.getDescription());
        assertTrue(session.getAttendanceHistory().isEmpty());
    }

    @Test
    public void toModelType_nullStart_throwsIllegalValueException() {
        JsonAdaptedScheduledSession adaptedSession = new JsonAdaptedScheduledSession(
                null,
                "2026-01-20T08:00:00",
                "WEEKLY",
                "Algebra",
                null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "session start");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedSession::toModelType);
    }

    @Test
    public void toModelType_invalidStart_throwsIllegalValueException() {
        JsonAdaptedScheduledSession adaptedSession = new JsonAdaptedScheduledSession(
                "2026-13-40T25:00:00",
                "2026-01-20T08:00:00",
                "WEEKLY",
                "Algebra",
                null);

        assertThrows(IllegalValueException.class, adaptedSession::toModelType);
    }

    @Test
    public void toModelType_invalidNext_throwsIllegalValueException() {
        JsonAdaptedScheduledSession adaptedSession = new JsonAdaptedScheduledSession(
                "2026-01-13T08:00:00",
                "2026-13-40T25:00:00",
                "WEEKLY",
                "Algebra",
                null);

        assertThrows(IllegalValueException.class, adaptedSession::toModelType);
    }

    @Test
    public void toModelType_invalidRecurrence_throwsIllegalValueException() {
        JsonAdaptedScheduledSession adaptedSession = new JsonAdaptedScheduledSession(
                "2026-01-13T08:00:00",
                "2026-01-20T08:00:00",
                "YEARLY",
                "Algebra",
                null);

        assertThrows(IllegalValueException.class,
                "Session recurrence must be one of: WEEKLY, BIWEEKLY, MONTHLY, NONE",
                adaptedSession::toModelType);
    }

    @Test
    public void toModelType_dateOnlyAttendanceRecord_supported() throws Exception {
        JsonAdaptedScheduledSession adaptedSession = new JsonAdaptedScheduledSession(
                "2026-01-13T08:00:00",
                null,
                null,
                "Algebra",
                List.of(new JsonAdaptedAppointmentAttendance(true, "2026-01-29")));

        ScheduledSession session = adaptedSession.toModelType();

        assertEquals(1, session.getAttendanceHistory().getRecords().size());
        Attendance expected = new Attendance(true, LocalDate.of(2026, 1, 29));
        assertEquals(expected, session.getAttendanceHistory().getRecords().get(0));
    }
}
