package test;

import java.util.Date;
import model.Appointment;
import model.AppointmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentServiceTest {
    private String id;
    private String description;
    private String tooLongDescription;
    private Date date;
    private Date pastDate;

    AppointmentServiceTest() {
    }

    @BeforeEach
    void setUp() {
        this.id = "2986695890";
        this.description = "This is the required description.";
        this.date = new Date(3050, 6, 11);
        this.tooLongDescription = "This is an example of a description that is too long and does not meet requirements.";
        this.pastDate = new Date(0L);
    }

    @Test
    void testNewAppointment() {
        AppointmentService service = new AppointmentService();
        service.newAppointment();
        Assertions.assertNotNull(((Appointment)service.getAppointmentList().get(0)).getAppointmentId());
        Assertions.assertNotNull(((Appointment)service.getAppointmentList().get(0)).getAppointmentDate());
        Assertions.assertNotNull(((Appointment)service.getAppointmentList().get(0)).getDescription());
        service.newAppointment(this.date);
        Assertions.assertNotNull(((Appointment)service.getAppointmentList().get(1)).getAppointmentId());
        Assertions.assertEquals(this.date, ((Appointment)service.getAppointmentList().get(1)).getAppointmentDate());
        Assertions.assertNotNull(((Appointment)service.getAppointmentList().get(1)).getDescription());
        service.newAppointment(this.date, this.description);
        Assertions.assertNotNull(((Appointment)service.getAppointmentList().get(2)).getAppointmentId());
        Assertions.assertEquals(this.date, ((Appointment)service.getAppointmentList().get(2)).getAppointmentDate());
        Assertions.assertEquals(this.description, ((Appointment)service.getAppointmentList().get(2)).getDescription());
        Assertions.assertNotEquals(((Appointment)service.getAppointmentList().get(0)).getAppointmentId(), ((Appointment)service.getAppointmentList().get(1)).getAppointmentId());
        Assertions.assertNotEquals(((Appointment)service.getAppointmentList().get(0)).getAppointmentId(), ((Appointment)service.getAppointmentList().get(2)).getAppointmentId());
        Assertions.assertNotEquals(((Appointment)service.getAppointmentList().get(1)).getAppointmentId(), ((Appointment)service.getAppointmentList().get(2)).getAppointmentId());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.newAppointment(this.pastDate);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.newAppointment(this.date, this.tooLongDescription);
        });
    }

    @Test
    void deleteAppointment() throws Exception {
        AppointmentService service = new AppointmentService();
        service.newAppointment();
        service.newAppointment();
        service.newAppointment();
        String firstId = ((Appointment)service.getAppointmentList().get(0)).getAppointmentId();
        String secondId = ((Appointment)service.getAppointmentList().get(1)).getAppointmentId();
        String thirdId = ((Appointment)service.getAppointmentList().get(2)).getAppointmentId();
        Assertions.assertNotEquals(firstId, secondId);
        Assertions.assertNotEquals(firstId, thirdId);
        Assertions.assertNotEquals(secondId, thirdId);
        Assertions.assertNotEquals(this.id, firstId);
        Assertions.assertNotEquals(this.id, secondId);
        Assertions.assertNotEquals(this.id, thirdId);
        Assertions.assertThrows(Exception.class, () -> {
            service.deleteAppointment(this.id);
        });
        service.deleteAppointment(firstId);
        Assertions.assertThrows(Exception.class, () -> {
            service.deleteAppointment(firstId);
        });
        Assertions.assertNotEquals(firstId, ((Appointment)service.getAppointmentList().get(0)).getAppointmentId());
    }
}
