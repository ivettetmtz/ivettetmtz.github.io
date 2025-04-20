package test;

import java.util.Date;
import model.Appointment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentTest {
    private String id;
    private String description;
    private String IDLong;
    private String descriptionLong;
    private Date date;
    private Date pastDate;

    AppointmentTest() {
    }

    @BeforeEach
    void setUp() {
        this.id = "2986695890";
        this.description = "This is the required description.";
        this.date = new Date(3050, 6, 11);
        this.IDLong = "13216849816848951984544668";
        this.descriptionLong = "This is an example of a description that is too long and does not meet requirements.";
        this.pastDate = new Date(0L);
    }

    @Test
    void testUpdateAppointmentId() {
        Appointment appt = new Appointment();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appt.updateAppointmentId((String)null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appt.updateAppointmentId(this.IDLong);
        });
        appt.updateAppointmentId(this.id);
        Assertions.assertEquals(this.id, appt.getAppointmentId());
    }

    @Test
    void testGetAppointmentId() {
        Appointment appt = new Appointment(this.id);
        Assertions.assertNotNull(appt.getAppointmentId());
        Assertions.assertEquals(appt.getAppointmentId().length(), 10);
        Assertions.assertEquals(this.id, appt.getAppointmentId());
    }

    @Test
    void testUpdateDate() {
        Appointment appt = new Appointment();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appt.updateDate((Date)null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appt.updateDate(this.pastDate);
        });
        appt.updateDate(this.date);
        Assertions.assertEquals(this.date, appt.getAppointmentDate());
    }

    @Test
    void testGetAppointmentDate() {
        Appointment appt = new Appointment(this.id, this.date);
        Assertions.assertNotNull(appt.getAppointmentDate());
        Assertions.assertEquals(this.date, appt.getAppointmentDate());
    }

    @Test
    void testUpdateDescription() {
        Appointment appt = new Appointment();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appt.updateDescription((String)null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appt.updateDescription(this.descriptionLong);
        });
        appt.updateDescription(this.description);
        Assertions.assertEquals(this.description, appt.getDescription());
    }

    @Test
    void testGetDescription() {
        Appointment appt = new Appointment(this.id, this.date, this.description);
        Assertions.assertNotNull(appt.getDescription());
        Assertions.assertTrue(appt.getDescription().length() <= 50);
        Assertions.assertEquals(this.description, appt.getDescription());
    }
}
