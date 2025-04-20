package main.java.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AppointmentService {
  final private List<Appointment> appointmentList = new ArrayList<>();

  private String newUniqueId() {
    return UUID.randomUUID().toString().substring(
        0, Math.min(toString().length(), 10));
  }
//checking to add a new unique ID
  public void newAppointment() {
    Appointment appt = new Appointment(newUniqueId());
    appointmentList.add(appt);
  }
//checking to add a new unique date
  public void newAppointment(Date date) {
    Appointment appt = new Appointment(newUniqueId(), date);
    appointmentList.add(appt);
  }
//checking to add a new unique description
  public void newAppointment(Date date, String description) {
    Appointment appt = new Appointment(newUniqueId(), date, description);
    appointmentList.add(appt);
  }
//checking to delete appointment
  public void deleteAppointment(String id) throws Exception {
    appointmentList.remove(searchForAppointment(id));
  }

  public List<Appointment> getAppointmentList() { return appointmentList; }

  private Appointment searchForAppointment(String id) throws Exception {
    int index = 0;
    while (index < appointmentList.size()) {
      if (id.equals(appointmentList.get(index).getAppointmentId())) {
        return appointmentList.get(index);
      }
      index++;
    }
    throw new Exception("The appointment does not exist!");
  }
}