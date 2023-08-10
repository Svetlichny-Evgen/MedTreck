package com.example.myapplication.patient;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private int nurseId;
    private int procedureId;
    private int medicationId;
    private String appointmentDate;

    public Appointment(){}

    public Appointment(int id, int patientId, int doctorId, int nurseId, int procedureId, int medicationId, String appointmentDate ) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.nurseId = nurseId;
        this.procedureId = procedureId;
        this.medicationId = medicationId;
        this.appointmentDate = appointmentDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getNurseId() {
        return nurseId;
    }
    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public int getProcedureId() {
        return procedureId;
    }
    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public int getMedicationId() { return medicationId; }
    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}

