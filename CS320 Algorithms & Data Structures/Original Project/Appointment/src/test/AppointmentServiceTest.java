package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.model.AppointmentService;

class AppointmentServiceTest {
  private String id, description, tooLongDescription;
  private Date date, pastDate;

  @SuppressWarnings("deprecation")
  @BeforeEach
  void setUp() {
    id = "2986695890";
    description = "This is the required description.";
    date = new Date(3050, Calendar.JULY, 11);
    tooLongDescription =
        "This is an example of a description that is too long and does not meet requirements.";
    pastDate = new Date(0);
  }

  @Test
  void testNewAppointment() {
    AppointmentService service = new AppointmentService();

    service.newAppointment();
    assertNotNull(service.getAppointmentList().get(0).getAppointmentId());
    assertNotNull(service.getAppointmentList().get(0).getAppointmentDate());
    assertNotNull(service.getAppointmentList().get(0).getDescription());

    service.newAppointment(date);
    assertNotNull(service.getAppointmentList().get(1).getAppointmentId());
    assertEquals(date,
                 service.getAppointmentList().get(1).getAppointmentDate());
    assertNotNull(service.getAppointmentList().get(1).getDescription());

    service.newAppointment(date, description);
    assertNotNull(service.getAppointmentList().get(2).getAppointmentId());
    assertEquals(date,
                 service.getAppointmentList().get(2).getAppointmentDate());
    assertEquals(description,
                 service.getAppointmentList().get(2).getDescription());

    assertNotEquals(service.getAppointmentList().get(0).getAppointmentId(),
                    service.getAppointmentList().get(1).getAppointmentId());
    assertNotEquals(service.getAppointmentList().get(0).getAppointmentId(),
                    service.getAppointmentList().get(2).getAppointmentId());
    assertNotEquals(service.getAppointmentList().get(1).getAppointmentId(),
                    service.getAppointmentList().get(2).getAppointmentId());

    assertThrows(IllegalArgumentException.class,
                 () -> service.newAppointment(pastDate));
    assertThrows(IllegalArgumentException.class,
                 () -> service.newAppointment(date, tooLongDescription));
  }

  @Test
  void deleteAppointment() throws Exception {
    AppointmentService service = new AppointmentService();

    service.newAppointment();
    service.newAppointment();
    service.newAppointment();

    String firstId = service.getAppointmentList().get(0).getAppointmentId();
    String secondId = service.getAppointmentList().get(1).getAppointmentId();
    String thirdId = service.getAppointmentList().get(2).getAppointmentId();

    assertNotEquals(firstId, secondId);
    assertNotEquals(firstId, thirdId);
    assertNotEquals(secondId, thirdId);
    assertNotEquals(id, firstId);
    assertNotEquals(id, secondId);
    assertNotEquals(id, thirdId);

    assertThrows(Exception.class, () -> service.deleteAppointment(id));

    service.deleteAppointment(firstId);
    assertThrows(Exception.class, () -> service.deleteAppointment(firstId));
    assertNotEquals(firstId,
                    service.getAppointmentList().get(0).getAppointmentId());
  }
}
