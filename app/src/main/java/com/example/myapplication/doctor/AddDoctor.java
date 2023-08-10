package com.example.myapplication.doctor;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.Choice;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.patient.AddPatient;

public class AddDoctor extends AppCompatActivity {

    EditText fio;
    EditText specification;
    EditText phone;
    EditText email;
    EditText password;
    Button add;
    private int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        adminId = getIntent().getIntExtra("admin_id", -1);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        fio = findViewById(R.id.fioEditText);
        specification = findViewById(R.id.specializationEditText);
        phone = findViewById(R.id.phoneNumberEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        add = findViewById(R.id.addDoctorButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String docFio = fio.getText().toString();
                String docSpec = specification.getText().toString();
                String docPhone = phone.getText().toString();
                String docEmail = email.getText().toString();
                String docPass = password.getText().toString();

                boolean result = myDB.addDoctorData(docFio, docSpec, docPhone, docEmail, adminId, docPass);

                if (result) {
                    Toast.makeText(AddDoctor.this, "Доктор успешно добавлен", Toast.LENGTH_SHORT).show();
                    // Здесь можно выполнить дополнительные действия после успешного добавления пациента
                } else {
                    Toast.makeText(AddDoctor.this, "Ошибка при добавлении доктора", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}