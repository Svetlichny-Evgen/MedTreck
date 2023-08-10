package com.example.myapplication.nurse;

public class Nurse {
    private int id;
    private String name;
    private String email;
    private String password;
    private String shiftStart;
    private String shiftEnd;
    private int admin_id;


    public Nurse() { }

    public Nurse(int id, String name, String email, String password, String shiftStart, String shiftEnd, int admin_id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.admin_id = admin_id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}


    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    public String getShiftStart() { return shiftStart; }
    public void setShiftStart(String shiftStart) { this.shiftStart = shiftStart; }


    public String getShiftEnd() { return shiftEnd; }
    public void setShiftEnd(String shiftEnd) { this.shiftEnd = shiftEnd; }

    public int getAdminId() { return admin_id; }
    public void setAdminId(int admin_id) { this.admin_id = admin_id; }
}
