package com.example.myapplication.doctor;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.doctor.Doctor;

public class DoctorInfo extends AppCompatActivity {

    private TextView fullNameTextView;
    private TextView specificationTextView;
    private TextView phoneNumberTextView;
    private TextView emailTextView;
    Doctor doctor = new Doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        String doctorName = getIntent().getStringExtra("doctor_name");

        fullNameTextView = findViewById(R.id.full_name_textview);
        specificationTextView = findViewById(R.id.specification_textview);
        phoneNumberTextView = findViewById(R.id.phone_number_textview);
        emailTextView = findViewById(R.id.email_textview);

        DatabaseHelper myDB = new DatabaseHelper(this);

        Cursor cursor = myDB.getDoctorDataByName(doctorName);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                doctor.setId(Integer.parseInt(cursor.getString(0)));
                doctor.setName(cursor.getString(1));
                doctor.setSpecification(cursor.getString(2));
                doctor.setPhone(cursor.getString(3));
                doctor.setEmail(cursor.getString(4));
                doctor.setAdminId(Integer.parseInt(cursor.getString(5)));
                doctor.setPassword(cursor.getString(6));
            }
        }
        fullNameTextView.setText(doctor.getName());
        specificationTextView.setText(doctor.getSpecification());
        phoneNumberTextView.setText(doctor.getPhone());
        emailTextView.setText(doctor.getEmail());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем текущую активити и возвращаемся на предыдущую
            }
        });
    }
}