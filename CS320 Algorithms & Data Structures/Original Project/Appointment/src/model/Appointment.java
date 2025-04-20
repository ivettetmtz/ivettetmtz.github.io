package main.java.model;
import java.util.Date;

public class Appointment {

  final private byte IDLength;
  final private byte descriptionLength;
  final private String initializer;
  private String appointmentID;
  private Date appointmentDate;
  private String description;

  {
    IDLength = 10;
    descriptionLength = 50;
    initializer = "INITIAL";
  }

  public Appointment() {
    Date today = new Date();
    appointmentID = initializer;
    appointmentDate = today;
    description = initializer;
  }

  public Appointment(String id) {
    Date today = new Date();
    updateAppointmentId(id);
    appointmentDate = today;
    description = initializer;
  }

  public Appointment(String id, Date date) {
    updateAppointmentId(id);
    updateDate(date);
    description = initializer;
  }

  public Appointment(String id, Date date, String description) {
    updateAppointmentId(id);
    updateDate(date);
    updateDescription(description);
  }
//updating the appt ID that should not be null nor be updated
  public void updateAppointmentId(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Appointment ID cannot be null.");
    } else if (id.length() > IDLength) {
      throw new IllegalArgumentException("Appointment ID cannot exceed " +
    		  IDLength +
                                         " characters.");
    } else {
      this.appointmentID = id;
    }
  }

  public String getAppointmentId() { return appointmentID; }
//update date on the appt, checking to see if null or not past the date
  public void updateDate(Date date) {
    if (date == null) {
      throw new IllegalArgumentException("Appointment date cannot be null.");
    } else if (date.before(new Date())) {
      throw new IllegalArgumentException(
          "Cannot make appointment in the past.");
    } else {
      this.appointmentDate = date;
    }
  }

  public Date getAppointmentDate() { return appointmentDate; }
//checking if description is not null and is not longer than the necessary characters
  public void updateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException(
          "Appointment description cannot be null.");
    } else if (description.length() > descriptionLength) {
      throw new IllegalArgumentException(
          "Appointment description cannot exceed " +
        		  descriptionLength + " characters.");
    } else {
      this.description = description;
    }
  }

  public String getDescription() { return description; }
}
