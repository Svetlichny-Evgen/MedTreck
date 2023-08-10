package com.example.myapplication.doctor;

public class Doctor {
    private int id;
    private String name;
    private String specification;
    private String phone;
    private String email;
    private int adminId;
    private String password;

    public Doctor() { }

    public Doctor(int id, String name, String specification, String phone, String email, int adminId, String password) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.phone = phone;
        this.email = email;
        this.adminId = adminId;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }
}
