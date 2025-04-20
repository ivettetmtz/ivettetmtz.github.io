package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AppointmentService {
    private final List<Appointment> appointmentList = new ArrayList();

    public AppointmentService() {
    }

    private String newUniqueId() {
        return UUID.randomUUID().toString().substring(0, Math.min(this.toString().length(), 10));
    }

    public void newAppointment() {
        Appointment appt = new Appointment(this.newUniqueId());
        this.appointmentList.add(appt);
    }

    public void newAppointment(Date date) {
        Appointment appt = new Appointment(this.newUniqueId(), date);
        this.appointmentList.add(appt);
    }

    public void newAppointment(Date date, String description) {
        Appointment appt = new Appointment(this.newUniqueId(), date, description);
        this.appointmentList.add(appt);
    }

    public void deleteAppointment(String id) throws Exception {
        this.appointmentList.remove(this.searchForAppointment(id));
    }

    public List<Appointment> getAppointmentList() {
        return this.appointmentList;
    }

    private Appointment searchForAppointment(String id) throws Exception {
        for(int index = 0; index < this.appointmentList.size(); ++index) {
            if (id.equals(((Appointment)this.appointmentList.get(index)).getAppointmentId())) {
                return (Appointment)this.appointmentList.get(index);
            }
        }

        throw new Exception("The appointment does not exist!");
    }
}
