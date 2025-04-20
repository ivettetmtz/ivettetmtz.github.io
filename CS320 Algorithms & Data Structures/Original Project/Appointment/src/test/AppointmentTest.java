package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.model.Appointment;

class AppointmentTest {

  private String id, description;
  private String IDLong, descriptionLong;
  private Date date, pastDate;

  @SuppressWarnings("deprecation")
  @BeforeEach
  void setUp() {
    id = "2986695890";
    description = "This is the required description.";
    date = new Date(3050, Calendar.JULY, 11);
    IDLong = "13216849816848951984544668";
    descriptionLong =
        "This is an example of a description that is too long and does not meet requirements.";
    pastDate = new Date(0);
  }

  @Test
  void testUpdateAppointmentId() {
    Appointment appt = new Appointment();
    assertThrows(IllegalArgumentException.class,
                 () -> appt.updateAppointmentId(null));
    assertThrows(IllegalArgumentException.class,
                 () -> appt.updateAppointmentId(IDLong));
    appt.updateAppointmentId(id);
    assertEquals(id, appt.getAppointmentId());
  }

  @Test
  void testGetAppointmentId() {
    Appointment appt = new Appointment(id);
    assertNotNull(appt.getAppointmentId());
    assertEquals(appt.getAppointmentId().length(), 10);
    assertEquals(id, appt.getAppointmentId());
  }

  @Test
  void testUpdateDate() {
    Appointment appt = new Appointment();
    assertThrows(IllegalArgumentException.class, () -> appt.updateDate(null));
    assertThrows(IllegalArgumentException.class,
                 () -> appt.updateDate(pastDate));
    appt.updateDate(date);
    assertEquals(date, appt.getAppointmentDate());
  }

  @Test
  void testGetAppointmentDate() {
    Appointment appt = new Appointment(id, date);
    assertNotNull(appt.getAppointmentDate());
    assertEquals(date, appt.getAppointmentDate());
  }

  @Test
  void testUpdateDescription() {
    Appointment appt = new Appointment();
    assertThrows(IllegalArgumentException.class,
                 () -> appt.updateDescription(null));
    assertThrows(IllegalArgumentException.class,
                 () -> appt.updateDescription(descriptionLong));
    appt.updateDescription(description);
    assertEquals(description, appt.getDescription());
  }

  @Test
  void testGetDescription() {
    Appointment appt = new Appointment(id, date, description);
    assertNotNull(appt.getDescription());
    assertTrue(appt.getDescription().length() <= 50);
    assertEquals(description, appt.getDescription());
  }
}
