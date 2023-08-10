package com.example.myapplication.patient;

public class Patient {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int doctorId;
    private int patientRoom;
    public Patient() { }
    public Patient(int id, String name, String address, String phone, String email, int doctorId, int patientRoom) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.doctorId = doctorId;
        this.patientRoom = patientRoom;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientRoom() {
        return patientRoom;
    }
    public void setPatientRoom(int patientRoom) {
        this.patientRoom = patientRoom;
    }
}
