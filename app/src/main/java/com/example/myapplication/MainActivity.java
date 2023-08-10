package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.admin.Admin;
import com.example.myapplication.admin.AdminActivity;
import com.example.myapplication.doctor.Doctor;
import com.example.myapplication.doctor.DoctorActivity;
import com.example.myapplication.nurse.Nurse;
import com.example.myapplication.nurse.NurseActivity;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText login_edit_text;
    EditText password_edit_text;
    Button login_button;
    String role = null;

    DatabaseHelper myDB;
    ArrayList<Admin> admins = new ArrayList<>();
    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Nurse> nurses = new ArrayList<>();
    Admin admin = new Admin();
    Nurse nurse = new Nurse();
    Doctor doctor = new Doctor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_edit_text = findViewById(R.id.login_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        login_button = findViewById(R.id.login_button);

        myDB = new DatabaseHelper(this);
        try {
            myDB.copyDatabase(); // копируем базу данных из assets
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myDB.openDatabase(); // открываем базу данных
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authorization(login_edit_text.getText().toString(), password_edit_text.getText().toString());
            }
        });
    }

    void Authorization(String email, String password) {

        Cursor cursor = myDB.getAllAdminsData();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                admin.setId(Integer.parseInt(cursor.getString(0)));
                admin.setName( cursor.getString(1));
                admin.setEmail( cursor.getString(2));
                admin.setPassword(cursor.getString(3));
                if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                    if (admin.getId() == 1) {
                        // Назначить роль супер-администратора
                        role = "admin";
                        checkRole(role);
                        Toast.makeText(this, "Супер-адміністратор: " + admin.getName(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Назначить роль администратора
                        role = "admin";
                        checkRole(role);
                        Toast.makeText(this, "Адміністратор: " + admin.getName(), Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        }

        if (role == null) {
            Cursor doctorCursor = myDB.getAllDoctorsData();
            if (doctorCursor.getCount() > 0) {
                while (doctorCursor.moveToNext()) {
                    doctor.setId(Integer.parseInt(doctorCursor.getString(0)));
                    doctor.setName(doctorCursor.getString(1));
                    doctor.setSpecification(doctorCursor.getString(2));
                    doctor.setPhone(doctorCursor.getString(3));
                    doctor.setEmail(doctorCursor.getString(4));
                    doctor.setAdminId(Integer.parseInt(doctorCursor.getString(5)));
                    doctor.setPassword(doctorCursor.getString(6));
                    if (doctor.getEmail().equals(email) && doctor.getPassword().equals(password)) {
                        role = "doctor";
                        checkRole(role);
                        Toast.makeText(this, "Лікар: " + doctor.getName(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }

        if (role == null) {
            Cursor nurseCursor = myDB.getAllNursesData();
            if (nurseCursor.getCount() > 0) {
                while (nurseCursor.moveToNext()) {
                    nurse.setId(Integer.parseInt(nurseCursor.getString(0)));
                    nurse.setName(nurseCursor.getString(1));
                    nurse.setEmail(nurseCursor.getString(2));
                    nurse.setPassword(nurseCursor.getString(3));
                    nurse.setShiftStart(nurseCursor.getString(4));
                    nurse.setShiftEnd(nurseCursor.getString(5));
                    if (nurse.getEmail().equals(email) && nurse.getPassword().equals(password)) {
                        role = "nurse";
                        checkRole(role);
                        Toast.makeText(this, "Мед. Сестра: " + nurse.getName(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }

        if (role == null) {
            Toast.makeText(this, "Неправильно введені логін чи пароль", Toast.LENGTH_SHORT).show();
        }
    }
    
    void checkRole(String role){
        if (role.equals("nurse")) {
            Intent intent = new Intent(this, NurseActivity.class);
            intent.putExtra("fio", nurse.getName());
            startActivity(intent);
            finish();
        } else if (role.equals("admin")) {
            // Переходим на AdminActivity
            Intent intent = new Intent(this, AdminActivity.class);

            intent.putExtra("fio", admin.getName());
            startActivity(intent);
            finish();
        } else if (role.equals("doctor")) {
            // Переходим на DoctorActivity
            Intent intent = new Intent(this, DoctorActivity.class);
            intent.putExtra("fio", doctor.getName());
            startActivity(intent);
            finish();
        } else {
            // Роль не определена или не поддерживается, оставляем пользователя в MainActivity
            Toast.makeText(this, "Invalid role", Toast.LENGTH_SHORT).show();
        }
    }
}