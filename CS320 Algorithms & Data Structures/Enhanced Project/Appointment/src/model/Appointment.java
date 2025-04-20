package model;

import java.util.Date;

public class Appointment {
    private final byte IDLength = 10;
    private final byte descriptionLength = 50;
    private final String initializer = "INITIAL";
    private String appointmentID;
    private Date appointmentDate;
    private String description;

    public Appointment() {
        Date today = new Date();
        this.appointmentID = this.initializer;
        this.appointmentDate = today;
        this.description = this.initializer;
    }

    public Appointment(String id) {
        Date today = new Date();
        this.updateAppointmentId(id);
        this.appointmentDate = today;
        this.description = this.initializer;
    }

    public Appointment(String id, Date date) {
        this.updateAppointmentId(id);
        this.updateDate(date);
        this.description = this.initializer;
    }

    public Appointment(String id, Date date, String description) {
        this.updateAppointmentId(id);
        this.updateDate(date);
        this.updateDescription(description);
    }

    public void updateAppointmentId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null.");
        } else if (id.length() > this.IDLength) {
            throw new IllegalArgumentException("Appointment ID cannot exceed " + this.IDLength + " characters.");
        } else {
            this.appointmentID = id;
        }
    }

    public String getAppointmentId() {
        return this.appointmentID;
    }

    public void updateDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Appointment date cannot be null.");
        } else if (date.before(new Date())) {
            throw new IllegalArgumentException("Cannot make appointment in the past.");
        } else {
            this.appointmentDate = date;
        }
    }

    public Date getAppointmentDate() {
        return this.appointmentDate;
    }

    public void updateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Appointment description cannot be null.");
        } else if (description.length() > this.descriptionLength) {
            throw new IllegalArgumentException("Appointment description cannot exceed " + this.descriptionLength + " characters.");
        } else {
            this.description = description;
        }
    }

    public String getDescription() {
        return this.description;
    }
}
